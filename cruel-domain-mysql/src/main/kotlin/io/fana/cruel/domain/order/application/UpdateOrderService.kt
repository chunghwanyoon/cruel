package io.fana.cruel.domain.order.application

import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.application.CreateReturnScheduleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class UpdateOrderService(
    private val getOrderService: GetOrderService,
    private val createReturnScheduleService: CreateReturnScheduleService,
) {
    fun approveOrder(request: AdminOrderRequest, date: LocalDateTime): Order {
        val order = getOrderService.getOrderById(request.orderId)
        createReturnScheduleService.createReturnSchedules(order, date)
        return order.approved(request.adminId, request.memo)
    }
}
