package io.fana.cruel.domain.order.domain

import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.exception.InvalidOrderTermException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class OrderTermTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    given("주문 기간이 주어졌을 때") {
        `when`("주문 기간을 생성하면") {
            then("생성된다") {
                val orderTerm = OrderTerm(3)
                orderTerm.term shouldBe 3
            }
        }
        
        `when`("주문 기간이 최소 주문 기간보다 작으면") {
            then("예외가 발생한다") {
                shouldThrow<InvalidOrderTermException> {
                    OrderTerm(1)
                }
            }
        }

        `when`("주문 기간이 최대 주문 기간보다 크면") {
            then("예외가 발생한다") {
                shouldThrow<InvalidOrderTermException> {
                    OrderTerm(24)
                }
            }
        }
    }
})
