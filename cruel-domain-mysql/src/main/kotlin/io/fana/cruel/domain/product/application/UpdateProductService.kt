package io.fana.cruel.domain.product.application

import io.fana.cruel.domain.product.domain.Product
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UpdateProductService(
    private val getProductService: GetProductService,
) {
    fun update(productId: Long, request: UpdateProductRequest): Product {
        val product = getProductService.getProductById(productId)
        return product.update(request)
    }
}
