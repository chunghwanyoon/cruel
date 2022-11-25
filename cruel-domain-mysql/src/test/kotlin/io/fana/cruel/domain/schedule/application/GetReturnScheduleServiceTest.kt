package io.fana.cruel.domain.schedule.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class GetReturnScheduleServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val returnScheduleRepository = mockk<ReturnScheduleRepository>()
    val getUserService = mockk<GetUserService>()
    val getReturnScheduleService = GetReturnScheduleService(
        returnScheduleRepository = returnScheduleRepository,
        getUserService = getUserService,
    )
    val userFixture = fixture<User>()
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(12) }
        property(Order::user) { userFixture }
    }
    val returnScheduleFixtures = fixture<List<ReturnSchedule>> {
        property(ReturnSchedule::orderId) { orderFixture.id }
    }
    val summarizedInformation = fixture<SummarizedRepaymentInformation>()

    given("주문이 주어졌을 때") {
        every { returnScheduleRepository.getAllByOrderId(orderFixture.id) } returns returnScheduleFixtures

        `when`("해당 주문의 상환 스케쥴을 조회하려고 하면") {
            then("상환 스케쥴이 조회된다") {
                val schedules = getReturnScheduleService.getReturnSchedulesByOrderId(orderFixture.id)
                schedules.last().orderId shouldBe orderFixture.id
            }
        }
    }
    given("상환 스케쥴이 주어졌을 때") {
        every {
            returnScheduleRepository.getReturnScheduleById(returnScheduleFixtures.last().id)
        } returns returnScheduleFixtures.last()

        `when`("상환 스케쥴을 조회하면") {
            then("스케쥴이 조회된다") {
                val schedule = getReturnScheduleService.getReturnScheduleById(returnScheduleFixtures.last().id)
                schedule.id shouldBe returnScheduleFixtures.last().id
            }
        }
    }
    given("유저가 주어졌을 때") {
        every { getUserService.getUserById(userFixture.id) } returns userFixture
        every { returnScheduleRepository.getSummarizedReturnInformation(userFixture.id) } returns summarizedInformation

        `when`("해당 유저의 요약된 상환스 정보를 조회하면") {
            then("정보가 조회된다") {
                shouldNotThrowAny {
                    getReturnScheduleService.getSummarizedReturnInformation(userFixture.id)
                }
            }
        }
    }
})
