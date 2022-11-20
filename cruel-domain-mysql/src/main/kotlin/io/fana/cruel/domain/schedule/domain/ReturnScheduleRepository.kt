package io.fana.cruel.domain.schedule.domain

import org.springframework.stereotype.Repository

@Repository
interface ReturnScheduleRepository {
    fun save(returnSchedule: ReturnSchedule): ReturnSchedule
}
