package io.fana.cruel.domain.product.application

import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import io.fana.cruel.domain.product.exception.ProductNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetProductService(
    private val productRepository: ProductRepository,
) {
    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun getAllActivatedProducts(): List<Product> {
        return productRepository.findProductsByIsActivated(
            isActivated = true
        )
    }

    fun findProductByCode(code: String): Product? = productRepository.findProductByCode(code)

    fun getProductById(productId: Long): Product {
        return productRepository.findProductById(productId) ?: throw ProductNotFoundException.ofId(productId)
    }
}
