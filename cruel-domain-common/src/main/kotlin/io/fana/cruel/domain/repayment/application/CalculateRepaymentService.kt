package io.fana.cruel.domain.repayment.application

import io.fana.cruel.domain.repayment.RepaymentSchedule
import org.springframework.stereotype.Service
import kotlin.math.pow
import kotlin.math.roundToInt

@Service
class CalculateRepaymentService {
    /**
     * Calculate repayment schedule, assumes no grace period
     * @param totalPrincipal total principal of the loan
     * @param yearlyInterestRate yearly interest rate scale 0 ~ 1
     * @param term loan term
     * @return list of RepaymentSchedule
     * @see RepaymentSchedule
     */
    fun createRepaymentSchedules(
        totalPrincipal: Int,
        yearlyInterestRate: Double,
        term: Int,
    ): List<RepaymentSchedule> {
        var remainPrincipal = totalPrincipal
        val monthlyInterestRate = yearlyInterestRate / term
        // TODO: 월 상환 원리금이 항상 같도록 보정이 필요함
        return List(term) {
            var seqId = it + 1
            var interestAmount = (remainPrincipal * monthlyInterestRate).roundToInt()
            var monthlyPrincipalInterestAmount =
                getMonthlyPrincipalInterestAmount(remainPrincipal, term - it, yearlyInterestRate)
            var principal = monthlyPrincipalInterestAmount - interestAmount
            remainPrincipal -= principal
            RepaymentSchedule(
                seqId = seqId,
                principal = principal,
                interest = interestAmount,
                totalAmount = monthlyPrincipalInterestAmount,
            )
        }
    }

    /**
     * 월에 납입해야 할 상환 원리금 계산
     * @param amount 잔금
     * @param term 남은 상계기간
     * @param monthlyInterestRate 월 단위 이자율
     * @return 월 상환 원리금
     */
    private fun getMonthlyPrincipalInterestAmount(
        amount: Int,
        term: Int,
        monthlyInterestRate: Double,
    ): Int {
        val base = (1 + monthlyInterestRate).pow(term.toDouble()) - 1
        return (amount * (monthlyInterestRate + (monthlyInterestRate / base))).roundToInt()
    }

    companion object {
        private const val MONTHLY = 12
    }
}
