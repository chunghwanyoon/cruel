package io.fana.cruel.core.exception

open class BusinessException(
    val statusCode: HttpStatusCode,
    val errorCode: ErrorCode,
    message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
