package io.fana.cruel.core.exception.client

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.HttpStatusCode

open class UnauthorizedAccessException(
    errorCode: ErrorCode,
    message: String? = null,
    cause: Throwable? = null,
) : ClientException(HttpStatusCode.FORBIDDEN, errorCode, message, cause)
