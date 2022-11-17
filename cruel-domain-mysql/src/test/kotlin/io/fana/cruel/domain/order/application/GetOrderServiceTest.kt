package io.fana.cruel.domain.order.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderRepository
import io.fana.cruel.domain.order.exception.OrderNotFoundException
import io.fana.cruel.domain.user.domain.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class GetOrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val orderRepository = mockk<OrderRepository>()
    val getOrderService = GetOrderService(
        orderRepository = orderRepository,
    )
    val fixture = kotlinFixture()
    val userFixture = fixture<User>()

    /**
     * TODO:
     * fixture를 만드는 헬퍼 매서드로 분리
     * javafaker랑 같이 사용하는 방식도 나쁘지 않을 듯 한데..
     */
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(3) }
    }

    given("유저가 주어졌을 때") {
        every { orderRepository.findAllByUserId(userFixture.id) } returns listOf()
        `when`("전체 주문을 조회하면") {
            then("전체 주문이 조회된다") {
                getOrderService.getOrdersByUserId(userFixture.id)
            }
        }

        `when`("특정 주문을 조회하면") {
            every { orderRepository.findOrderById(orderFixture.id) } returns orderFixture
            then("주문이 조회된다") {
                val order = getOrderService.getOrderById(orderFixture.id)
                order.id shouldBe orderFixture.id
            }
        }

        `when`("주문이 존재하지 않으면") {
            every { orderRepository.findOrderById(orderFixture.id) } returns null
            then("예외가 발생한다") {
                shouldThrow<OrderNotFoundException> {
                    getOrderService.getOrderById(orderFixture.id)
                }
            }
        }
    }
})
