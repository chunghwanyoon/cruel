package io.fana.cruel.domain.order.application

data class CreateOrderRequest(
    val productId: Long,
    val userId: Long,
    val term: Int,
    val content: String,
)
