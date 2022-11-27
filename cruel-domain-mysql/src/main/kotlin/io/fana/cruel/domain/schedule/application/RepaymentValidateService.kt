package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import io.fana.cruel.domain.schedule.exception.RepaymentValidateFailedException
import org.springframework.stereotype.Service

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
        requireScheduleNotCompleted(returnSchedule)
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
     * 상환하려는 스케쥴이 이미 완료상태인지 확인한다.
     * @param returnSchedule 상환 스케쥴
     * @throws RepaymentValidateFailedException
     */
    private fun requireScheduleNotCompleted(returnSchedule: ReturnSchedule) {
        if (returnSchedule.isReturned) {
            throw RepaymentValidateFailedException("이미 상환완료된 상환건입니다.")
        }
    }
}
