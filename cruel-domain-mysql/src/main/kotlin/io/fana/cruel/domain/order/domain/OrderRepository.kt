package io.fana.cruel.domain.order.domain

interface OrderRepository {
    fun findAllByUserId(userId: Long): List<Order>

    fun findOrderById(orderId: Long): Order?

    fun save(order: Order): Order
}
