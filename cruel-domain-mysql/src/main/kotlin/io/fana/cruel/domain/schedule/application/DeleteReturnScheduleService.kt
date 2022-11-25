package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.infra.JpaReturnScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DeleteReturnScheduleService(
    private val returnScheduleRepository: JpaReturnScheduleRepository,
    private val getReturnScheduleService: GetReturnScheduleService,
) {
    fun deleteReturnSchedulesByOrderId(orderId: Long) {
        val returnSchedules = getReturnScheduleService.getReturnSchedulesByOrderId(orderId)
        returnScheduleRepository.deleteAll(returnSchedules)
    }
}
