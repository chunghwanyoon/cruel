package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.kyc.exception.PhoneAuthenticationVerifyFailedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class VerifyPhoneAuthenticationService(
    private val getPhoneAuthenticationService: GetPhoneAuthenticationService,
) {
    fun verify(userId: Long, verifyCode: String): PhoneAuthentication {
        verifyAuthenticateCode(verifyCode)
        val auth = getPhoneAuthenticationService.getPhoneAuthenticationByUserId(userId)
        return auth.authenticate()
    }

    private fun verifyAuthenticateCode(verifyCode: String) {
        // TODO: request to external API for verifying code
        if (verifyCode != VERIFIED_TEMP_CODE) {
            throw PhoneAuthenticationVerifyFailedException()
        }
    }

    companion object {
        private const val VERIFIED_TEMP_CODE = "000000"
    }
}
