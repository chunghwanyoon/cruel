package io.fana.cruel.domain.order.application

import io.fana.cruel.domain.schedule.application.DeleteReturnScheduleService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Transactional
@Service
class DeleteOrderService(
    private val getOrderService: GetOrderService,
    private val deleteReturnScheduleService: DeleteReturnScheduleService,
) {
    fun rejectOrder(orderId: Long) {
        val order = getOrderService.getOrderById(orderId)
        order.rejected()
        deleteReturnScheduleService.deleteReturnSchedulesByOrderId(order.id)
    }
}
