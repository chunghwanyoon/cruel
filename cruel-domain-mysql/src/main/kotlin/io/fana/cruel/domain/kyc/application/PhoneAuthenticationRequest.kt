package io.fana.cruel.domain.kyc.application

import io.fana.cruel.domain.type.MobileCode

data class PhoneAuthenticationRequest(
    val name: String,
    val phoneNumber: String,
    val mobileCode: MobileCode,
    val rrnFirst: String,
    val rrnLast: String,
)
