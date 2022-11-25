package io.fana.cruel.domain.order.domain

import io.fana.cruel.core.type.DelayStatusSearchFilter
import io.fana.cruel.core.type.OrderStatusSearchFilter

interface OrderQueryRepository {
    fun findOrderByStatusAndDelayStatusFilters(
        statusFilter: OrderStatusSearchFilter,
        delayedFilter: DelayStatusSearchFilter,
        lastSeen: Long,
        limit: Int,
    ): List<Order>
}
