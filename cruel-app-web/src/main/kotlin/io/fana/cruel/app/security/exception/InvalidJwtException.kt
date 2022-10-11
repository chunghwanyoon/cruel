package io.fana.cruel.app.security.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class InvalidJwtException(
    message: String? = "잘못된 JWT 입니다.",
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.INVALID_JWT_TOKEN, message, cause)
