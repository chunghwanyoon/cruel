package io.fana.cruel.domain.product.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import io.fana.cruel.domain.product.exception.ProductDuplicatedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class CreateProductServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val productRepository = mockk<ProductRepository>()
    val getProductService = mockk<GetProductService>()
    val createProductService = CreateProductService(
        productRepository = productRepository,
        getProductService = getProductService,
    )
    val productCode = "SAMPLE_CODE"
    val productFixture = fixture<Product> {
        property(Product::name) { "TEST_PRODUCT" }
        property(Product::code) { productCode }
        property(Product::price) { 500_000 }
        property(Product::imageUrl) { "https://test.com" }
        property(Product::isActivated) { true }
    }
    val request = CreateProductRequest(
        name = productFixture.name,
        code = productFixture.code,
        price = productFixture.price,
        imageUrl = productFixture.imageUrl,
    )

    given("상품 정보가 주어졌을 때") {
        every { productRepository.save(productFixture) } returns productFixture
        every { getProductService.findProductByCode(productCode) } returns null

        `when`("상품을 생성하면") {
            then("상품이 생성된다") {
                val product = createProductService.createProduct(request)
                product.id shouldBe productFixture.id
                product.isActivated shouldBe true
                product.name shouldBe productFixture.name
                product.code shouldBe productFixture.code
            }
        }

        `when`("상품 코드가 중복되면") {
            every { getProductService.findProductByCode(productCode) } returns productFixture

            then("예외가 발생한다") {
                shouldThrow<ProductDuplicatedException> {
                    createProductService.createProduct(request)
                }
            }
        }
    }
})
