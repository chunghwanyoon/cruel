package io.fana.cruel.domain.product.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class DeleteProductServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getProductService = mockk<GetProductService>()
    val productRepository = mockk<ProductRepository>()
    val deleteProductService = DeleteProductService(
        productRepository = productRepository,
        getProductService = getProductService
    )
    val productFixture = fixture<Product>()

    given("주문이 주어졌을 때") {
        every { getProductService.getProductById(productFixture.id) } returns productFixture
        every { productRepository.deleteProductById(productFixture.id) } returns Unit

        `when`("주문을 삭제하면") {
            then("삭제된다") {
                shouldNotThrowAny {
                    deleteProductService.delete(productFixture.id)
                }
            }
        }
    }
})
