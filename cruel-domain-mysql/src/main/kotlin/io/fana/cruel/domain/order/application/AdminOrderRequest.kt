package io.fana.cruel.domain.order.application

data class AdminOrderRequest(
    val orderId: Long,
    val adminId: Long,
    val memo: String?,
)
