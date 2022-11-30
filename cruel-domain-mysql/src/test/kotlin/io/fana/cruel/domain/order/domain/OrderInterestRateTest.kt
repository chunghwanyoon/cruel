package io.fana.cruel.domain.order.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

internal class OrderInterestRateTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val interestRate = BigDecimal.valueOf(0.1)

    given("주문 이자율이 주어졌을 때") {
        `when`("주문 이자율을 생성하면") {
            then("주문 이자율이 생성된다") {
                val orderInterestRate = OrderInterestRate(interestRate)

                orderInterestRate.rate shouldBe interestRate
            }
        }

        `when`("주문 이자율이 0보다 작으면") {
            then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    OrderInterestRate(BigDecimal.valueOf(-0.1))
                }
            }
        }
    }
})
