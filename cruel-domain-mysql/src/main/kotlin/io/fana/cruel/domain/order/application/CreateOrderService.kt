package io.fana.cruel.domain.order.application

import io.fana.cruel.domain.order.OrderInterestRate
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderRepository
import io.fana.cruel.domain.user.application.GetUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateOrderService(
    private val orderRepository: OrderRepository,
    private val getUserService: GetUserService,
) {
    fun requestOrder(request: CreateOrderRequest): Order {
        val user = getUserService.getUserById(request.userId)
        val order = Order(
            user = user,
            amount = request.amount,
            interestRate = generateOrderInterestRate(),
            orderTerm = OrderTerm(request.term),
            content = request.content
        )
        return orderRepository.save(order)
    }

    private fun generateOrderInterestRate() = OrderInterestRate()
}
