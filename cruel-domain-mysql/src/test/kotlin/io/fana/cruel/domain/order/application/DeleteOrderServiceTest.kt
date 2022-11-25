package io.fana.cruel.domain.order.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.application.DeleteReturnScheduleService
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class DeleteOrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getOrderService = mockk<GetOrderService>()
    val deleteReturnScheduleService = mockk<DeleteReturnScheduleService>()
    val deleteOrderService = DeleteOrderService(
        getOrderService = getOrderService,
        deleteReturnScheduleService = deleteReturnScheduleService,
    )
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(3) }
    }

    given("주문이 주어졌을 때") {
        every { getOrderService.getOrderById(orderFixture.id) } returns orderFixture
        every { deleteReturnScheduleService.deleteReturnSchedulesByOrderId(orderFixture.id) } returns Unit

        `when`("주문을 거절하면") {
            then("주문이 거절되고, 상환 스케쥴이 삭제된다") {
                shouldNotThrowAny {
                    deleteOrderService.rejectOrder(orderFixture.id)
                }
            }
        }
    }
})
