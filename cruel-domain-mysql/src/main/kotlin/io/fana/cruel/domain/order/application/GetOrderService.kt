package io.fana.cruel.domain.order.application

import io.fana.cruel.core.type.DelayStatusSearchFilter
import io.fana.cruel.core.type.OrderStatusSearchFilter
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderQueryRepository
import io.fana.cruel.domain.order.domain.OrderRepository
import io.fana.cruel.domain.order.exception.OrderNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetOrderService(
    private val orderRepository: OrderRepository,
    private val orderQueryRepository: OrderQueryRepository,
) {
    fun getOrders(
        statusFilter: OrderStatusSearchFilter,
        delayedFilter: DelayStatusSearchFilter,
        lastSeen: Long,
        limit: Int,
    ): List<Order> {
        return orderQueryRepository.findOrderByStatusAndDelayStatusFilters(
            statusFilter,
            delayedFilter,
            lastSeen,
            limit,
        )
    }

    fun getOrdersByUserId(userId: Long): List<Order> = orderRepository.findAllByUserId(userId)

    fun getOrderById(orderId: Long): Order = orderRepository.findOrderById(orderId)
        ?: throw OrderNotFoundException.ofId(orderId)
}
