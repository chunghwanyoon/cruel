package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.repayment.application.CalculateRepaymentService
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.infra.JpaReturnScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class CreateReturnScheduleService(
    private val calculateRepaymentService: CalculateRepaymentService,
    private val returnScheduleRepository: JpaReturnScheduleRepository,
) {
    fun createReturnSchedules(order: Order, date: LocalDateTime): List<ReturnSchedule> {
        val repaymentSchedules = calculateRepaymentService.createRepaymentSchedules(
            order.amount,
            order.interestRate.toDouble(),
            order.term
        )
        val returnSchedules = repaymentSchedules.map {
            ReturnSchedule(
                orderId = order.id,
                principal = it.principal,
                interest = it.interest,
                seqId = it.seqId,
                scheduledAt = date.plusMonths(it.seqId.toLong())
            )
        }
        return returnScheduleRepository.saveAll(returnSchedules)
    }
}
