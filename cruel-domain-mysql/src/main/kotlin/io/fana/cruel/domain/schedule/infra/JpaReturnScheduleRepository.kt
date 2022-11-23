package io.fana.cruel.domain.schedule.infra

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaReturnScheduleRepository : ReturnScheduleRepository, JpaRepository<ReturnSchedule, Long> {
    override fun save(returnSchedule: ReturnSchedule): ReturnSchedule
}
