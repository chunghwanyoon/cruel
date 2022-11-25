package io.fana.cruel.domain.schedule.application

import java.time.LocalDateTime

data class SummarizedRepaymentInformation(
    val totalPrincipal: Int,
    val totalInterest: Int,
    val totalAmount: Int = totalPrincipal + totalInterest,
    val scheduledAt: LocalDateTime,
)
