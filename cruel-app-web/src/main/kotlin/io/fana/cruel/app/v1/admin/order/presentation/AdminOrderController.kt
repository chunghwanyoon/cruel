package io.fana.cruel.app.v1.admin.order.presentation

import io.fana.cruel.core.type.DelayStatusSearchFilter
import io.fana.cruel.core.type.OrderStatusSearchFilter
import io.fana.cruel.domain.order.application.AdminOrderRequest
import io.fana.cruel.domain.order.application.DeleteOrderService
import io.fana.cruel.domain.order.application.GetOrderService
import io.fana.cruel.domain.order.application.UpdateOrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "어드민 주문", description = "어드민 주문 관리 API")
@RestController
@RequestMapping("/api/v1/admin/orders")
class AdminOrderController(
    private val getOrderService: GetOrderService,
    private val updateOrderService: UpdateOrderService,
    private val deleteOrderService: DeleteOrderService,
) {
    @Operation(summary = "getAdminOrders", description = "어드민 주문 목록 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    @GetMapping
    fun getAdminOrders(
        @RequestParam(defaultValue = "ALL") statusFilter: OrderStatusSearchFilter,
        @RequestParam(defaultValue = "ALL") delayedFilter: DelayStatusSearchFilter,
        @RequestParam(defaultValue = Long.MAX_VALUE.toString()) lastSeen: Long,
        @RequestParam(defaultValue = "20") limit: Int,
    ): List<AdminOrderResponse> {
        return AdminOrderResponse.of(
            getOrderService.getOrders(
                statusFilter = statusFilter,
                delayedFilter = delayedFilter,
                lastSeen = lastSeen,
                limit = limit,
            )
        )
    }

    @PostMapping("/approve")
    @Operation(summary = "approveOrder", description = "어드민 주문 승인")
    @ApiResponse(description = "요청 성공", responseCode = "201")
    fun approveOrder(
        @RequestBody request: AdminOrderRequest,
    ): AdminOrderResponse {
        return AdminOrderResponse.of(updateOrderService.approveOrder(request, LocalDateTime.now()))
    }

    @DeleteMapping("/reject")
    @Operation(summary = "rejectOrder", description = "어드민 주문 거절")
    @ApiResponse(description = "요청 성공", responseCode = "204")
    fun rejectOrder(
        @RequestBody request: AdminOrderRequest,
    ) = deleteOrderService.rejectOrder(request)
}
