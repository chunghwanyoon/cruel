package io.fana.cruel.domain.order.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class OrderNotFoundException(
    message: String?,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.ORDER_NOT_FOUND, message, cause) {
    companion object {
        fun ofId(orderId: Long) = OrderNotFoundException("주문이 존재하지 않습니다. id=$orderId")
    }
}
