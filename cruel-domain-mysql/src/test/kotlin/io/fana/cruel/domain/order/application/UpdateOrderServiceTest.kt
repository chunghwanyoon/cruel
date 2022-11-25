package io.fana.cruel.domain.order.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.application.CreateReturnScheduleService
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

internal class UpdateOrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getOrderService = mockk<GetOrderService>()
    val createReturnScheduleService = mockk<CreateReturnScheduleService>()
    val updateOrderService = UpdateOrderService(
        getOrderService = getOrderService,
        createReturnScheduleService = createReturnScheduleService,
    )
    val now = LocalDateTime.now()
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(12) }
    }
    val returnScheduleFixtures = fixture<List<ReturnSchedule>> {
        property(ReturnSchedule::orderId) { orderFixture.id }
    }

    given("주문이 주어졌을 때") {
        every { getOrderService.getOrderById(orderFixture.id) } returns orderFixture
        every { createReturnScheduleService.createReturnSchedules(orderFixture, now) } returns returnScheduleFixtures

        `when`("주문을 승인 처리하면") {
            then("주문이 승인되고 상환 스케쥴이 생성된다") {
                val order = updateOrderService.approveOrder(orderFixture.id, now)

                order.status shouldBe OrderStatus.APPROVED
            }
        }
    }
})
