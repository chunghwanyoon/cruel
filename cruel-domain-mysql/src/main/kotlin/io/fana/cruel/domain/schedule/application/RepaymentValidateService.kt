package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import io.fana.cruel.domain.schedule.exception.RepaymentValidateFailedException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RepaymentValidateService(
    private val returnScheduleRepository: ReturnScheduleRepository,
) {
    /**
     * 상환이 가능한지 검증한다.
     * @param returnSchedule 상환 스케줄
     * @throws RepaymentValidateFailedException
     */
    fun validate(returnSchedule: ReturnSchedule) {
        requirePreviousRepaymentCompleted(returnSchedule)
        scheduledAtWithInWeeks(returnSchedule.scheduledAt)
    }

    /**
     * 이전 상환건이 상환 완료 상태인지 확인한다.
     * @param returnSchedule 상환 스케쥴
     * @throws RepaymentValidateFailedException
     */
    private fun requirePreviousRepaymentCompleted(returnSchedule: ReturnSchedule) {
        val notRepaidSchedules = returnScheduleRepository.getPreviousNotRepaidSchedules(
            returnSchedule.orderId,
            returnSchedule.id
        )
        if (notRepaidSchedules.isNotEmpty()) {
            throw RepaymentValidateFailedException("이전 상환 스케쥴 중 완료되지 않은 상환건이 존재합니다.")
        }
    }

    /**
     * 상환하려는 일자가 상환 예정일 7일 내인지 확인한다.
     * @param scheduledAt 해당 상환 스케쥴의 정해진 상환 기일
     * @throws RepaymentValidateFailedException
     */
    private fun scheduledAtWithInWeeks(scheduledAt: LocalDateTime) {
        val now = LocalDateTime.now()
        if (now.plusDays(REPAYMENT_WITH_IN_POSSIBLE_DAYS) < scheduledAt) {
            throw RepaymentValidateFailedException("실제 상환 예정일로부터 7일 이내에만 상환처리가 가능합니다.")
        }
    }

    companion object {
        private const val REPAYMENT_WITH_IN_POSSIBLE_DAYS = 7L
    }
}
