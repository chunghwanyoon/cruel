package io.fana.cruel.app.v1.admin.order.presentation

import io.fana.cruel.core.type.DelayStatus
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.order.domain.Order
import java.math.BigDecimal

data class AdminOrderResponse(
    val id: Long,
    val userId: Long,
    val adminId: Long,
    val amount: Int,
    val interestRate: BigDecimal,
    val term: Int,
    val status: OrderStatus,
    val content: String? = null,
    val delayStatus: DelayStatus,
) {
    companion object {
        fun of(order: Order) = AdminOrderResponse(
            id = order.id,
            userId = order.user.id,
            adminId = order.adminId ?: 0,
            amount = order.amount,
            interestRate = order.interestRate,
            term = order.term,
            status = order.status,
            content = order.content,
            delayStatus = order.delayStatus,
        )

        fun of(orders: List<Order>): List<AdminOrderResponse> = orders.map { of(it) }
    }
}
