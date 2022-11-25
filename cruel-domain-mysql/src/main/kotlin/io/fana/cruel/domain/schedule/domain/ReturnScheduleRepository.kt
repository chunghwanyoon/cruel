package io.fana.cruel.domain.schedule.domain

import org.springframework.stereotype.Repository

@Repository
interface ReturnScheduleRepository {
    fun getAllByOrderId(orderId: Long): List<ReturnSchedule>

    fun save(returnSchedule: ReturnSchedule): ReturnSchedule
}
