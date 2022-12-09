package io.fana.cruel.domain.kyc.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationVerifyFailedException
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

internal class VerifyPhoneAuthenticationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getPhoneAuthService = mockk<GetPhoneAuthenticationService>()
    val verifyPhoneAuthService = VerifyPhoneAuthenticationService(
        getPhoneAuthenticationService = getPhoneAuthService,
    )
    val phoneNumber = "01012345678"
    val mobileCode = MobileCode.values().random()
    val verifyCode = "000000"
    val user = fixture<User>()
    val unverifiedPhoneAuth = fixture<PhoneAuthentication> {
        property(PhoneAuthentication::userId) { user.id }
        property(PhoneAuthentication::unverifiedMobileCode) { mobileCode }
        property(PhoneAuthentication::unverifiedPhoneNumber) { phoneNumber }
    }

    given("유저와 인증코드 6자리가 주어졌을 때") {
        `when`("인증 코드가 일치하면") {
            every { getPhoneAuthService.getPhoneAuthenticationByUserId(user.id) } returns unverifiedPhoneAuth

            then("휴대폰 인증이 완료된다") {
                withContext(Dispatchers.IO) {
                    val auth = verifyPhoneAuthService.verify(user.id, verifyCode)
                    auth.isAuthenticated() shouldBe true
                    auth.phoneNumber shouldBe phoneNumber
                    auth.mobileCode shouldBe mobileCode
                }
            }
        }

        `when`("인증 코드가 일치하지 않으면") {
            then("예외가 발생한다") {
                val invalidVerifyCode = "999999"
                shouldThrow<PhoneAuthenticationVerifyFailedException> {
                    verifyPhoneAuthService.verify(user.id, invalidVerifyCode)
                }
            }
        }
    }
})
