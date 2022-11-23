package io.fana.cruel.domain.repayment

data class RepaymentSchedule(
    val seqId: Int,
    val principal: Int,
    val interest: Int,
    val totalAmount: Int,
)
