package io.fana.cruel.domain.schedule.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.user.domain.User
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class UpdateReturnScheduleServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getReturnScheduleService = mockk<GetReturnScheduleService>()
    val repaymentValidateService = mockk<RepaymentValidateService>()
    val updateReturnScheduleService = UpdateReturnScheduleService(
        getReturnScheduleService = getReturnScheduleService,
        repaymentValidateService = repaymentValidateService,
    )
    val userFixture = fixture<User>()
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(12) }
        property(Order::user) { userFixture }
    }
    val returnScheduleFixture = fixture<ReturnSchedule> {
        property(ReturnSchedule::orderId) { orderFixture.id }
    }

    given("상환 스케쥴이 주어졌을 때") {
        every {
            getReturnScheduleService.getReturnScheduleById(returnScheduleFixture.id)
        } returns returnScheduleFixture
        every { repaymentValidateService.validate(any()) } returns Unit

        `when`("상환처리를 하면") {
            then("상환처리가 완료된다") {
                updateReturnScheduleService.repayReturnSchedule(returnScheduleFixture.id)

                returnScheduleFixture.id shouldBe returnScheduleFixture.id
                returnScheduleFixture.isReturned shouldBe true
            }
        }
    }
})
