package io.fana.cruel.domain.schedule.application

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetReturnScheduleService(
    private val returnScheduleRepository: ReturnScheduleRepository,
) {
    fun getReturnSchedulesByOrderId(orderId: Long): List<ReturnSchedule> {
        return returnScheduleRepository.getAllByOrderId(orderId)
    }
}
