package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import io.fana.cruel.domain.user.application.GetUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetReturnScheduleService(
    private val returnScheduleRepository: ReturnScheduleRepository,
    private val getUserService: GetUserService,
) {
    fun getReturnSchedulesByOrderId(orderId: Long): List<ReturnSchedule> {
        return returnScheduleRepository.getAllByOrderId(orderId)
    }

    fun getReturnScheduleById(scheduleId: Long): ReturnSchedule {
        return returnScheduleRepository.getReturnScheduleById(scheduleId)
    }

    fun getSummarizedReturnInformation(userId: Long): SummarizedRepaymentInformation {
        val user = getUserService.getUserById(userId)
        return returnScheduleRepository.getSummarizedReturnInformation(user.id)
    }
}
