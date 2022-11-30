package io.fana.cruel.domain.product.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.product.domain.Product
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class UpdateProductServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getProductService = mockk<GetProductService>()
    val updateProductService = UpdateProductService(
        getProductService = getProductService
    )
    val productFixture = fixture<Product>()
    val request = fixture<UpdateProductRequest>()

    given("상품이 주어졌을 때") {
        every { getProductService.getProductById(productFixture.id) } returns productFixture

        `when`("상품을 수정하려고 하면") {
            then("수정된다") {
                val updatedProduct = updateProductService.update(productFixture.id, request)
                updatedProduct.name shouldBe request.name
                updatedProduct.price shouldBe request.price
                updatedProduct.code shouldBe request.code
                updatedProduct.imageUrl shouldBe request.imageUrl
                updatedProduct.isActivated shouldBe request.isActivated
            }
        }
    }
})
