package io.fana.cruel.domain.repayment

import io.fana.cruel.domain.repayment.application.CalculateRepaymentService
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec

internal class CalculateRepaymentServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val calculateRepaymentService = CalculateRepaymentService()
    val principals = listOf(500_000, 1_000_000, 1_500_000, 2_000_000)
    val interestRates = listOf(0.05, 0.1, 0.15, 0.2)
    val terms = listOf(3, 6, 9, 12)

    given("총 원금, 연 이자율, 대출 기간이 주어졌을 때") {
        `when`("상환 계획을 계산하면") {
            then("상환 계획이 반환된다") {
                val results = calculateRepaymentService.calculateRepayment(
                    totalPrincipal = principals.random(),
                    yearlyInterestRate = interestRates.random(),
                    term = terms.random(),
                )
                for (result in results) {
                    println(result.toString())
                }
            }
        }
    }
})
