package io.fana.cruel.app.v1.order.presentation

import io.fana.cruel.app.security.LoginUser
import io.fana.cruel.domain.order.application.GetOrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "주문 API", description = "Order API")
@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val getOrderService: GetOrderService,
) {
    @Operation(operationId = "getOrders", description = "유저의 주문 목록 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    @GetMapping
    fun getOrders(
        @Parameter(hidden = true)
        loginUser: LoginUser,
    ): List<OrderResponse> {
        return OrderResponse.of(getOrderService.getOrdersByUserId(loginUser.id))
    }

    @Operation(operationId = "getOrderById", description = "유저의 특정 주문 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    @GetMapping("/{orderId}")
    fun getOrderById(
        @Parameter(hidden = true)
        loginUser: LoginUser,
        @PathVariable("orderId")
        @Parameter(name = "orderId", `in` = ParameterIn.PATH, description = "orderId", example = "1")
        orderId: Long,
    ): OrderResponse {
        return OrderResponse.of(getOrderService.getOrderById(orderId))
    }

    @PostMapping
    fun requestOrder() {
        TODO()
    }
}
