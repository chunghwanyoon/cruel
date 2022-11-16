package io.fana.cruel.domain.order.infra

import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaOrderRepository : OrderRepository, JpaRepository<Order, Long> {
    override fun findAllByUserId(userId: Long): List<Order>

    override fun findOrderById(orderId: Long): Order?

    override fun save(order: Order): Order
}
