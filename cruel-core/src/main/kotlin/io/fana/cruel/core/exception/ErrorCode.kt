package io.fana.cruel.core.exception

enum class ErrorCode(
    private val group: ErrorGroup,
    private val value: String,
    val messageKey: String,
) {
    /* 공통 */
    INVALID_INPUT(ErrorGroup.COMMON, "001", "ERR_INVALID_INPUT"),
    METHOD_NOT_ALLOWED(ErrorGroup.COMMON, "002", "ERR_METHOD_NOT_ALLOWED"),
    INTERNAL_SERVER_ERROR(ErrorGroup.COMMON, "999", "ERR_UNHANDLED_EXCEPTION"),

    /* 인증 */
    INVALID_JWT_TOKEN(ErrorGroup.AUTH, "001", "ERR_INVALID_JWT_TOKEN"),
    INVALID_INTERNAL_TOKEN(ErrorGroup.AUTH, "002", "ERR_INVALID_INTERNAL_TOKEN"),
    AUTHENTICATION_FAILED(ErrorGroup.AUTH, "003", "ERR_AUTHENTICATION_FAILED"),
    UNAUTHORIZED_USER(ErrorGroup.AUTH, "004", "ERR_UNAUTHORIZED_USER"),
    LOGIN_METHOD_NOT_FOUND(ErrorGroup.AUTH, "005", "ERR_LOGIN_METHOD_NOT_FOUND"),
    INVALID_PASSWORD(ErrorGroup.AUTH, "006", "ERR_INVALID_PASSWORD"),

    /* 유저 */
    USER_NOT_FOUND(ErrorGroup.USER, "001", "ERR_USER_NOT_FOUND"),
    INVALID_NICK_NAME(ErrorGroup.USER, "002", "ERR_INVALID_NICK_NAME");

    val errorCode: String
        get() = group.prefix + value
}
