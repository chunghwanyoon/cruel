package io.fana.cruel.domain.kyc.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.domain.PhoneAuthenticationRepository
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationNotFoundException
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

internal class GetPhoneAuthenticationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val phoneAuthenticationRepository = mockk<PhoneAuthenticationRepository>()
    val getPhoneAuthService = GetPhoneAuthenticationService(
        phoneAuthenticationRepository = phoneAuthenticationRepository,
    )
    val phoneNumber = "01012345678"
    val user = fixture<User>()
    val phoneAuth = fixture<PhoneAuthentication> {
        property(PhoneAuthentication::userId) { user.id }
        property(PhoneAuthentication::unverifiedPhoneNumber) { phoneNumber }
        property(PhoneAuthentication::unverifiedMobileCode) { MobileCode.values().random() }
    }

    given("유저가 주어졌을 때") {
        `when`("유저의 휴대폰 인증이 존재하면") {
            every { phoneAuthenticationRepository.findByUserId(user.id) } returns phoneAuth
            every { phoneAuthenticationRepository.findByPhoneNumber(phoneNumber) } returns phoneAuth

            then("휴대폰 인증 내역이 조회된다") {
                withContext(Dispatchers.IO) {
                    getPhoneAuthService.getPhoneAuthenticationByUserId(user.id) shouldBe phoneAuth
                }
            }

            then("휴대폰 인증 완료 여부가 조회된다") {
                val authenticated = withContext(Dispatchers.IO) {
                    getPhoneAuthService.isPhoneAuthenticationVerified(user.id)
                }
                authenticated::class shouldBe Boolean::class
            }
        }
        `when`("유저의 휴대폰 인증이 존재하지 않으면") {
            every { phoneAuthenticationRepository.findByUserId(user.id) } returns null
            every { phoneAuthenticationRepository.findByPhoneNumber(phoneNumber) } returns null

            then("휴대폰 인증 내역이 조회되지 않는다") {
                withContext(Dispatchers.IO) {
                    shouldThrow<PhoneAuthenticationNotFoundException> {
                        getPhoneAuthService.getPhoneAuthenticationByUserId(user.id)
                    }
                }
            }

            then("휴대폰 인증 완료 여부가 false 이다") {
                val authenticated = withContext(Dispatchers.IO) {
                    getPhoneAuthService.isPhoneAuthenticationVerified(user.id)
                }
                authenticated shouldBe false
            }
        }
    }

    given("휴대폰 번호가 주어졌을 때") {
        `when`("휴대폰 인증이 존재하면") {
            every { phoneAuthenticationRepository.findByUserId(user.id) } returns phoneAuth
            every { phoneAuthenticationRepository.findByPhoneNumber(phoneNumber) } returns phoneAuth

            then("휴대폰 인증 내역이 조회된다") {
                withContext(Dispatchers.IO) {
                    getPhoneAuthService.getPhoneAuthenticationByPhoneNumber(phoneNumber) shouldBe phoneAuth
                }
            }
        }

        `when`("휴대폰 인증이 존재하지 않으면") {
            every { phoneAuthenticationRepository.findByUserId(user.id) } returns null
            every { phoneAuthenticationRepository.findByPhoneNumber(phoneNumber) } returns null

            then("휴대폰 인증 내역이 조회되지 않는다") {
                withContext(Dispatchers.IO) {
                    shouldThrow<PhoneAuthenticationNotFoundException> {
                        getPhoneAuthService.getPhoneAuthenticationByPhoneNumber(phoneNumber)
                    }
                }
            }
        }
    }
})
