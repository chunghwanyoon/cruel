package io.fana.cruel.domain.order.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderRepository
import io.fana.cruel.domain.order.domain.OrderTerm
import io.fana.cruel.domain.product.application.GetProductService
import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreateOrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val orderRepository = mockk<OrderRepository>()
    val getUserService = mockk<GetUserService>()
    val getProductService = mockk<GetProductService>()
    val createOrderService = CreateOrderService(
        orderRepository = orderRepository,
        getUserService = getUserService,
        getProductService = getProductService,
    )
    val fixture = kotlinFixture()
    val userFixture = fixture<User>()
    val validOrderTerm = 3
    val content = ""
    val productPrice = 500_000
    val productFixture = fixture<Product> {
        property(Product::price) { productPrice }
    }
    val orderFixture = fixture<Order> {
        factory<OrderTerm> { OrderTerm(validOrderTerm) }
        property(Order::content) { content }
        property(Order::amount) { productPrice }
    }
    val orderRequest = CreateOrderRequest(
        userId = userFixture.id,
        productId = productFixture.id,
        term = validOrderTerm,
        content = content,
    )

    given("주문 요청이 주어졌을 때") {
        every { getUserService.getUserById(userFixture.id) } returns userFixture
        every { getProductService.getProductById(productFixture.id) } returns productFixture

        `when`("주문을 생성하면") {
            every { orderRepository.save(orderFixture) } returns orderFixture

            then("주문이 생성된다") {
                val order = withContext(Dispatchers.IO) {
                    createOrderService.requestOrder(orderRequest)
                }

                order.user.id shouldBe userFixture.id
                order.amount shouldBe productPrice
                order.term shouldBe validOrderTerm
                order.content shouldBe orderRequest.content
                order.status shouldBe OrderStatus.CREATED
            }
        }
    }
})
