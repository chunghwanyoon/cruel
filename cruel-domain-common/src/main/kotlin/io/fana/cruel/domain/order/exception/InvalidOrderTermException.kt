package io.fana.cruel.domain.order.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class InvalidOrderTermException(
    message: String?,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.INVALID_ORDER_TERM, message, cause) {
    companion object {
        fun of(min: Int, max: Int) = InvalidOrderTermException(
            "주문 기간은 $min ~ $max 사이여야 합니다."
        )
    }
}
