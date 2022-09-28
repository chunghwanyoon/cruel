package io.fana.cruel.core.exception

enum class ErrorCode(
    val group: ErrorGroup,
    private val value: String,
    val messageKey: String,
) {
    /* 공통 */
    INVALID_INPUT(ErrorGroup.COMMON, "001", "ERR_INVALID_INPUT"),
    METHOD_NOT_ALLOWED(ErrorGroup.COMMON, "002", "ERR_METHOD_NOT_ALLOWED"),
    INTERNAL_SERVER_ERROR(ErrorGroup.COMMON, "999", "ERR_UNHANDLED_EXCEPTION");

    val errorCode: String
        get() = group.prefix + value
}
