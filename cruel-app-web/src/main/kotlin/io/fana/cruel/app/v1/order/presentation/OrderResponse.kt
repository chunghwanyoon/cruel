package io.fana.cruel.app.v1.order.presentation

import io.fana.cruel.core.type.DelayStatus
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.order.domain.Order
import java.math.BigDecimal

data class OrderResponse(
    val id: Long,
    val amount: Int,
    val interestRate: BigDecimal,
    val term: Int,
    val status: OrderStatus,
    val content: String? = null,
    val delayStatus: DelayStatus,
) {
    companion object {
        fun of(order: Order) = OrderResponse(
            id = order.id,
            amount = order.amount,
            interestRate = order.interestRate,
            term = order.term,
            status = order.status,
            content = order.content,
            delayStatus = order.delayStatus,
        )

        fun of(orders: List<Order>): List<OrderResponse> = orders.map { of(it) }
    }
}
