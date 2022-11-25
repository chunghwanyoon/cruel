package io.fana.cruel.app.v1.schedule.presentation

import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import java.time.LocalDateTime

data class ReturnScheduleResponse(
    val orderId: Long,
    val seqId: Int,
    val principal: Int,
    val interest: Int,
    val totalAmount: Int,
    val isReturned: Boolean,
    val scheduledAt: LocalDateTime,
    val returnedAt: LocalDateTime?,
) {
    companion object {
        fun of(returnSchedule: ReturnSchedule) = ReturnScheduleResponse(
            orderId = returnSchedule.orderId,
            seqId = returnSchedule.seqId,
            principal = returnSchedule.principal,
            interest = returnSchedule.interest,
            totalAmount = returnSchedule.totalAmount,
            isReturned = returnSchedule.isReturned,
            scheduledAt = returnSchedule.scheduledAt,
            returnedAt = returnSchedule.returnedAt,
        )

        fun of(returnSchedules: List<ReturnSchedule>) = returnSchedules.map { of(it) }
    }
}
