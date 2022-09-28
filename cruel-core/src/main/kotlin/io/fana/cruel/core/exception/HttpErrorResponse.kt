package io.fana.cruel.core.exception
import java.util.Locale

data class HttpErrorResponse(
    val message: String,
    val code: String,
) {
    companion object {
        fun of(message: String, errorCode: ErrorCode) =
            HttpErrorResponse(message, errorCode.errorCode)
    }
}
