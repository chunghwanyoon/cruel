package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UpdateReturnScheduleService(
    private val getReturnScheduleService: GetReturnScheduleService,
) {
    fun repayReturnSchedule(scheduleId: Long): ReturnSchedule {
        val returnSchedule = getReturnScheduleService.getReturnScheduleById(scheduleId)
        returnSchedule.repay()
        return returnSchedule
    }
}
