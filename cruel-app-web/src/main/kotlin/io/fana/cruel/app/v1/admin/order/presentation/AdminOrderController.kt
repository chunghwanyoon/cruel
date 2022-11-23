package io.fana.cruel.app.v1.admin.order.presentation

import io.fana.cruel.core.type.DelayStatusSearchFilter
import io.fana.cruel.core.type.OrderStatusSearchFilter
import io.fana.cruel.domain.order.application.UpdateOrderService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "어드민 주문", description = "어드민 주문 관리 API")
@RestController
@RequestMapping("/api/v1/admin/orders")
class AdminOrderController(
    private val updateOrderService: UpdateOrderService,
) {
    fun getOrders(
        @RequestParam(defaultValue = "ALL") statusFilter: OrderStatusSearchFilter,
        @RequestParam(defaultValue = "ALL") delayedFilter: DelayStatusSearchFilter,
    ): List<AdminOrderResponse> {
        TODO()
    }

    @PostMapping("/{orderId}/approve")
    fun approveOrder(
        @Parameter(name = "orderId", `in` = ParameterIn.PATH, description = "주문 ID")
        @PathVariable("orderId") orderId: Long,
    ): AdminOrderResponse {
        return AdminOrderResponse.of(updateOrderService.approveOrder(orderId, LocalDateTime.now()))
    }
}
