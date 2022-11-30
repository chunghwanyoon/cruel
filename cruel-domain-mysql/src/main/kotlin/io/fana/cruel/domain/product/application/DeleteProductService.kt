package io.fana.cruel.domain.product.application

import io.fana.cruel.domain.product.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DeleteProductService(
    private val productRepository: ProductRepository,
    private val getProductService: GetProductService,
) {
    fun delete(productId: Long) {
        val product = getProductService.getProductById(productId)
        productRepository.deleteProductById(product.id)
    }
}
