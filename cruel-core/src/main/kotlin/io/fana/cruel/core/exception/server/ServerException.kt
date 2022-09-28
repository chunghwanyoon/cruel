package io.fana.cruel.core.exception.server

import io.fana.cruel.core.exception.BusinessException
import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.HttpStatusCode

open class ServerException(
    statusCode: HttpStatusCode,
    errorCode: ErrorCode,
    message: String? = null,
    cause: Throwable? = null,
) : BusinessException(statusCode, errorCode, message, cause)
