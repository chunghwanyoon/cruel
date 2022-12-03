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

    /* 어드민 */
    ADMIN_NOT_FOUND(ErrorGroup.ADMIN, "001", "ERR_ADMIN_NOT_FOUND"),
    UNAUTHORIZED_ADMIN(ErrorGroup.ADMIN, "002", "ERR_UNAUTHORIZED_ADMIN"),

    /* 인증 */
    INVALID_JWT_TOKEN(ErrorGroup.AUTH, "001", "ERR_INVALID_JWT_TOKEN"),
    INVALID_INTERNAL_TOKEN(ErrorGroup.AUTH, "002", "ERR_INVALID_INTERNAL_TOKEN"),
    AUTHENTICATION_FAILED(ErrorGroup.AUTH, "003", "ERR_AUTHENTICATION_FAILED"),
    UNAUTHORIZED_USER(ErrorGroup.AUTH, "004", "ERR_UNAUTHORIZED_USER"),
    LOGIN_METHOD_NOT_FOUND(ErrorGroup.AUTH, "005", "ERR_LOGIN_METHOD_NOT_FOUND"),
    INVALID_PASSWORD(ErrorGroup.AUTH, "006", "ERR_INVALID_PASSWORD"),

    /* 유저 */
    USER_NOT_FOUND(ErrorGroup.USER, "001", "ERR_USER_NOT_FOUND"),
    INVALID_NICK_NAME(ErrorGroup.USER, "002", "ERR_INVALID_NICK_NAME"),
    DUPLICATED_NICK_NAME(ErrorGroup.USER, "003", "ERR_DUPLICATED_NICK_NAME"),

    /* 주문 */
    INVALID_ORDER_STATUS(ErrorGroup.ORDER, "001", "ERR_INVALID_ORDER_STATUS"),
    ORDER_NOT_FOUND(ErrorGroup.ORDER, "002", "ERR_ORDER_NOT_FOUND"),
    INVALID_ORDER_TERM(ErrorGroup.ORDER, "003", "ERR_INVALID_ORDER_TERM"),

    /* 상환 */
    REPAYMENT_VALIDATE_FAILED(ErrorGroup.REPAYMENT, "001", "ERR_REPAYMENT_VALIDATE_FAILED"),

    /* 상품 */
    PRODUCT_NOT_FOUND(ErrorGroup.PRODUCT, "001", "ERR_PRODUCT_NOT_FOUND"),
    PRODUCT_CODE_DUPLICATED(ErrorGroup.PRODUCT, "002", "ERR_PRODUCT_CODE_DUPLICATED"),

    /* 배송 */
    DELIVERY_INFORMATION_NOT_FOUND(ErrorGroup.DELIVERY, "001", "ERR_DELIVERY_INFORMATION_NOT_FOUND");

    val errorCode: String
        get() = group.prefix + value
}
