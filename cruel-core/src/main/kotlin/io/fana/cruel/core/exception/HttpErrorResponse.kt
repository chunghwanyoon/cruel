package io.fana.cruel.core.exception

data class HttpErrorResponse(
    val message: String,
    val code: String,
) {
    companion object {
        fun of(message: String, errorCode: ErrorCode) =
            HttpErrorResponse(message, errorCode.errorCode)
    }
}
