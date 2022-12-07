package io.fana.cruel.app.domain

import java.time.LocalDateTime

data class ApproachingReturnSchedule(
    val userId: Long,
    val seqId: Int,
    val phoneNumber: String,
    val scheduledAt: LocalDateTime,
    val principal: Int,
    val interest: Int,
    val totalAmount: Int,
)
