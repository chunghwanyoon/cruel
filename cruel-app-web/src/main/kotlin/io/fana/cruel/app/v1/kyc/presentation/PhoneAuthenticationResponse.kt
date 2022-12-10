package io.fana.cruel.app.v1.kyc.presentation

import io.fana.cruel.domain.kyc.domain.PhoneAuthentication
import io.fana.cruel.domain.type.MobileCode

data class PhoneAuthenticationResponse(
    val name: String,
    val phoneNumber: String,
    val mobileCode: MobileCode,
) {
    companion object {
        fun of(phoneAuthentication: PhoneAuthentication) = PhoneAuthenticationResponse(
            name = phoneAuthentication.name,
            phoneNumber = phoneAuthentication.unverifiedPhoneNumber,
            mobileCode = phoneAuthentication.unverifiedMobileCode,
        )
    }
}
