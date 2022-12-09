package io.fana.cruel.domain.kyc.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class PhoneAuthenticationNotFoundException(
    message: String = "휴대폰 인증 내역을 찾을 수 없습니다.",
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.PHONE_AUTHENTICATION_NOT_FOUND, message, cause)
