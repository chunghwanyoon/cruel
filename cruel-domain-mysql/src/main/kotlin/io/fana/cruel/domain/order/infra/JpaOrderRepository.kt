package io.fana.cruel.domain.order.infra

import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaOrderRepository : OrderRepository, JpaRepository<Order, Long> {
    @Query(
        """
            SELECT o
            FROM Order o
            JOIN FETCH o.product
            WHERE o.user.id = :userId
        """
    )
    override fun findAllByUserId(userId: Long): List<Order>

    @Query(
        """
            SELECT o
            FROM Order o
            JOIN FETCH o.product
            where o.id = :orderId
        """
    )
    override fun findOrderById(orderId: Long): Order?

    override fun save(order: Order): Order
}
