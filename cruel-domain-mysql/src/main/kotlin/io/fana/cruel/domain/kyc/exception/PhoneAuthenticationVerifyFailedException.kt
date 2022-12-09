package io.fana.cruel.domain.kyc.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class PhoneAuthenticationVerifyFailedException(
    message: String = "휴대폰 인증 코드가 유효하지 않습니다.",
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.PHONE_AUTHENTICATION_VERIFY_FAILED, message, cause)
