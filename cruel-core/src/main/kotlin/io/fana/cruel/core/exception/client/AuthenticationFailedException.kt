package io.fana.cruel.core.exception.client

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.HttpStatusCode

open class AuthenticationFailedException(
    errorCode: ErrorCode,
    message: String? = "인증이 되지 않은 사용자입니다.",
    cause: Throwable? = null,
) : ClientException(HttpStatusCode.UNAUTHORIZED, errorCode, message, cause)
