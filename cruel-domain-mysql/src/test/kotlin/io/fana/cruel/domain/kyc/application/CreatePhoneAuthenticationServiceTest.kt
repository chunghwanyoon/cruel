package io.fana.cruel.domain.kyc.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.domain.PhoneAuthenticationRepository
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationAlreadyExistException
import io.fana.cruel.domain.type.MobileCode
import io.fana.cruel.domain.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreatePhoneAuthenticationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val phoneAuthRepository = mockk<PhoneAuthenticationRepository>()
    val phoneAuthValidatePolicy = mockk<PhoneAuthenticationValidatePolicy>()
    val getPhoneAuthService = mockk<GetPhoneAuthenticationService>()
    val createPhoneAuthService = CreatePhoneAuthenticationService(
        phoneAuthenticationRepository = phoneAuthRepository,
        phoneAuthenticationValidatePolicy = phoneAuthValidatePolicy,
        getPhoneAuthenticationService = getPhoneAuthService,
    )
    val phoneNumber = "01012345678"
    val user = fixture<User>()
    val rrnFirst = "000000"
    val rrnLast = "0000000"
    val unverifiedPhoneAuth = fixture<PhoneAuthentication> {
        property(PhoneAuthentication::userId) { user.id }
        property(PhoneAuthentication::unverifiedMobileCode) { MobileCode.values().random() }
        property(PhoneAuthentication::unverifiedPhoneNumber) { phoneNumber }
    }
    val request = fixture<PhoneAuthenticationRequest> {
        property(PhoneAuthenticationRequest::phoneNumber) { phoneNumber }
        property(PhoneAuthenticationRequest::rrnFirst) { rrnFirst }
        property(PhoneAuthenticationRequest::rrnLast) { rrnLast }
    }
    val alreadyVerifiedAuth = fixture<PhoneAuthentication> {
        property(PhoneAuthentication::userId) { user.id }
        property(PhoneAuthentication::unverifiedMobileCode) { MobileCode.values().random() }
        property(PhoneAuthentication::unverifiedPhoneNumber) { phoneNumber }
    }.authenticate()

    given("유저와 휴대폰 인증 요청이 주어졌을 때") {
        every { phoneAuthRepository.save(unverifiedPhoneAuth) } returns unverifiedPhoneAuth
        every { phoneAuthValidatePolicy.validate(phoneNumber, rrnFirst, rrnLast) } returns Unit

        `when`("인증 내역이 없을 때 인증을 요청하면") {
            every { getPhoneAuthService.findPhoneAuthenticationByUserId(user.id) } returns null
            every { getPhoneAuthService.findPhoneAuthenticationByPhoneNumber(phoneNumber) } returns null

            then("인증이 요청된다") {
                val auth = withContext(Dispatchers.IO) {
                    createPhoneAuthService.createPhoneAuth(user.id, request)
                }
                auth.unverifiedMobileCode shouldBe unverifiedPhoneAuth.unverifiedMobileCode
                auth.unverifiedPhoneNumber shouldBe unverifiedPhoneAuth.unverifiedPhoneNumber
                auth.phoneNumber shouldBe null
                auth.mobileCode shouldBe null
            }
        }

        `when`("인증 내역이 있을 때 인증을 요청하면") {
            every { getPhoneAuthService.findPhoneAuthenticationByUserId(user.id) } returns unverifiedPhoneAuth
            every { getPhoneAuthService.findPhoneAuthenticationByPhoneNumber(phoneNumber) } returns null
            then("인증이 요청된다") {
                val auth = withContext(Dispatchers.IO) {
                    createPhoneAuthService.createPhoneAuth(user.id, request)
                }
                auth.unverifiedMobileCode shouldBe unverifiedPhoneAuth.unverifiedMobileCode
                auth.unverifiedPhoneNumber shouldBe unverifiedPhoneAuth.unverifiedPhoneNumber
                auth.phoneNumber shouldBe null
                auth.mobileCode shouldBe null
            }
        }

        `when`("이미 인증 완료된 내역이 있으면") {
            every { getPhoneAuthService.findPhoneAuthenticationByUserId(user.id) } returns alreadyVerifiedAuth
            every { getPhoneAuthService.findPhoneAuthenticationByPhoneNumber(phoneNumber) } returns null

            then("예외가 발생한다") {
                shouldThrow<PhoneAuthenticationAlreadyExistException> {
                    createPhoneAuthService.createPhoneAuth(user.id, request)
                }
            }
        }

        `when`("해당 휴대폰번호로 이미 인증된 내역이 있다면") {
            every { getPhoneAuthService.findPhoneAuthenticationByUserId(user.id) } returns null
            every { getPhoneAuthService.findPhoneAuthenticationByPhoneNumber(phoneNumber) } returns alreadyVerifiedAuth
            then("예외가 발생한다") {
                shouldThrow<PhoneAuthenticationAlreadyExistException> {
                    createPhoneAuthService.createPhoneAuth(user.id, request)
                }
            }
        }
    }
})
