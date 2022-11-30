package io.fana.cruel.domain.order.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderTerm
import io.fana.cruel.domain.schedule.application.DeleteReturnScheduleService
import io.fana.cruel.domain.schedule.application.GetReturnScheduleService
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.infra.JpaReturnScheduleRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class DeleteReturnScheduleServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val returnScheduleRepository = mockk<JpaReturnScheduleRepository>()
    val getReturnScheduleService = mockk<GetReturnScheduleService>()
    val deleteReturnScheduleService = DeleteReturnScheduleService(
        returnScheduleRepository = returnScheduleRepository,
        getReturnScheduleService = getReturnScheduleService,
    )
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(12) }
    }
    val returnScheduleFixtures = fixture<List<ReturnSchedule>> {
        property(ReturnSchedule::orderId) { orderFixture.id }
    }

    given("주문이 주어졌을 때") {
        every { getReturnScheduleService.getReturnSchedulesByOrderId(orderFixture.id) } returns returnScheduleFixtures
        every { returnScheduleRepository.deleteAll(returnScheduleFixtures) } returns Unit

        `when`("상환 스케쥴을 삭제하면") {
            then("해당 주문의 상환 스케쥴이 삭제된다") {
                shouldNotThrowAny {
                    deleteReturnScheduleService.deleteReturnSchedulesByOrderId(orderFixture.id)
                }
            }
        }
    }
})
