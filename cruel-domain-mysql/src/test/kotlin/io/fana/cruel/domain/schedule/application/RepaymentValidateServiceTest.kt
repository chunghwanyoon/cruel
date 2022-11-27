package io.fana.cruel.domain.schedule.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import io.fana.cruel.domain.schedule.exception.RepaymentValidateFailedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class RepaymentValidateServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val returnScheduleRepository = mockk<ReturnScheduleRepository>()
    val repaymentValidateService = RepaymentValidateService(
        returnScheduleRepository = returnScheduleRepository
    )
    val term = 3
    val order = fixture<Order> {
        factory<OrderTerm> { OrderTerm(term) }
    }
    val notReturnedSchedules = fixture<List<ReturnSchedule>> {
        repeatCount { term }
        property(ReturnSchedule::orderId) { order.id }
        property(ReturnSchedule::isReturned) { false }
    }

    val returnedSchedules = fixture<List<ReturnSchedule>> {
        repeatCount { term }
        property(ReturnSchedule::orderId) { order.id }
        property(ReturnSchedule::isReturned) { true }
    }

    given("상환 스케쥴이 주어졌을 때") {
        `when`("이전 상환건이 상환 완료 상태가 아니라면") {
            every {
                returnScheduleRepository.getPreviousNotRepaidSchedules(
                    order.id,
                    any()
                )
            } returns notReturnedSchedules

            then("예외가 발생한다") {
                shouldThrow<RepaymentValidateFailedException> {
                    repaymentValidateService.validate(notReturnedSchedules.last())
                }
            }
        }

        `when`("이미 상환 완료된 건이라면") {
            every {
                returnScheduleRepository.getPreviousNotRepaidSchedules(
                    order.id,
                    any()
                )
            } returns returnedSchedules

            then("예외가 발생한다") {
                shouldThrow<RepaymentValidateFailedException> {
                    repaymentValidateService.validate(returnedSchedules.last())
                }
            }
        }
    }
})
