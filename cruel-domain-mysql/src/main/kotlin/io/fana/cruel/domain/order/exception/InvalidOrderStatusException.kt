package io.fana.cruel.domain.order.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException
import io.fana.cruel.core.type.OrderStatus

class InvalidOrderStatusException(
    message: String?,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.INVALID_ORDER_STATUS, message, cause) {
    companion object {
        fun of(
            before: OrderStatus,
            after: OrderStatus,
            cause: Throwable? = null,
        ) = InvalidOrderStatusException(
            "주문의 상태를 ${before}에서 ${after}로 변경할 수 없습니다.",
            cause
        )
    }
}
