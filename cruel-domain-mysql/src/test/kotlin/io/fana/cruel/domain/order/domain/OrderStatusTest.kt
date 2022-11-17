package io.fana.cruel.domain.order.domain

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.exception.InvalidOrderStatusException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

internal class OrderStatusTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val fixture = kotlinFixture()
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(3) }
    }

    given("주문이 주어졌을 때") {
        `when`("주문을 생성하면") {
            then("주문의 상태는 CREATED 이다") {
                orderFixture.status shouldBe OrderStatus.CREATED
            }
        }
        `when`("주문을 취소하면") {
            then("주문이 취소된다") {
                orderFixture.canceled()
                orderFixture.status shouldBe OrderStatus.CANCELED
            }
            given("주문이 CREATED가 아닐 때") {
                `when`("활성화된 주문의 취소를 시도하면") {
                    val activatedOrder = orderFixture.activated()
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            activatedOrder.canceled()
                        }
                    }
                }
                `when`("완료된 주문의 취소를 시도하면") {
                    val activatedOrder = orderFixture.activated()
                    val completedOrder = activatedOrder.completed()
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            completedOrder.canceled()
                        }
                    }
                }
            }
        }
        `when`("주문을 활성화하면") {
            then("주문이 활성화된다") {
                orderFixture.activated()
                orderFixture.status shouldBe OrderStatus.ACTIVATED
            }
            given("주문이 CREATED가 아닐 때") {
                `when`("취소된 주문의 활성화를 시도하면") {
                    val canceledOrder = orderFixture.canceled()
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            canceledOrder.activated()
                        }
                    }
                }
                `when`("완료된 주문의 활성화를 시도하면") {
                    val activatedOrder = orderFixture.activated()
                    val completedOrder = activatedOrder.completed()
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            completedOrder.activated()
                        }
                    }
                }
            }
        }
        `when`("주문을 완료하면") {
            val activatedOrder = orderFixture.activated()
            then("주문이 완료된다") {
                println(activatedOrder.status)
                activatedOrder.completed()
                activatedOrder.status shouldBe OrderStatus.COMPLETED
            }
            given("주문이 ACTIVATED가 아닐 때") {
                `when`("취소된 주문의 완료를 시도하면") {
                    val canceledOrder = orderFixture.canceled()
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            canceledOrder.completed()
                        }
                    }
                }
                `when`("생성된 주문의 완료를 시도하면") {
                    then("예외가 발생한다") {
                        shouldThrow<InvalidOrderStatusException> {
                            orderFixture.completed()
                        }
                    }
                }
            }
        }
    }
})
