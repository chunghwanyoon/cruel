package io.fana.cruel.domain.product.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import io.fana.cruel.domain.product.exception.ProductNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class GetProductServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val productRepository = mockk<ProductRepository>()
    val getProductService = GetProductService(
        productRepository = productRepository,
    )
    val productSize = 10
    val product = fixture<Product>()
    val products = fixture<List<Product>> {
        repeatCount { productSize }
    }

    given("제품들이 주어졌을 때") {
        `when`("제품 전체를 조회하면") {
            every { productRepository.findAll() } returns products
            then("제품 전체가 조회된다") {
                val products = getProductService.getAllProducts()
                products.size shouldBe productSize
            }
        }
    }

    given("제품이 주어졌을 때") {
        `when`("제품을 조회하면") {
            every { productRepository.findProductById(product.id) } returns product
            then("해당 제품이 조회된다") {
                val product = getProductService.getProductById(product.id)
                product.id shouldBe product.id
            }
        }

        `when`("제품이 존재하지 않으면") {
            every { productRepository.findProductById(product.id) } returns null
            then("예외가 발생한다") {
                shouldThrow<ProductNotFoundException> {
                    getProductService.getProductById(product.id)
                }
            }
        }
    }
})
