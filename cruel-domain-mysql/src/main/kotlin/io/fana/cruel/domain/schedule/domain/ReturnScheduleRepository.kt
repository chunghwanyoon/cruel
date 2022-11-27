package io.fana.cruel.domain.schedule.domain

import io.fana.cruel.domain.schedule.application.SummarizedRepaymentInformation
import org.springframework.stereotype.Repository

@Repository
interface ReturnScheduleRepository {
    fun getAllByOrderId(orderId: Long): List<ReturnSchedule>

    fun getReturnScheduleById(scheduleId: Long): ReturnSchedule

    fun getSummarizedReturnInformation(userId: Long): List<SummarizedRepaymentInformation>

    fun getPreviousNotRepaidSchedules(orderId: Long, returnScheduleId: Long): List<ReturnSchedule>

    fun save(returnSchedule: ReturnSchedule): ReturnSchedule
}
