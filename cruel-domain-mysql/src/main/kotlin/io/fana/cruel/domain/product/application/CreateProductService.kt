package io.fana.cruel.domain.product.application

import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import io.fana.cruel.domain.product.exception.ProductDuplicatedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateProductService(
    private val productRepository: ProductRepository,
    private val getProductService: GetProductService,
) {
    fun createProduct(request: CreateProductRequest): Product {
        requireProductCodeNotExists(request.code)
        val product = Product(
            name = request.name,
            code = request.code,
            price = request.price,
            imageUrl = request.imageUrl,
        )
        return productRepository.save(product)
    }

    private fun requireProductCodeNotExists(code: String) {
        val product = getProductService.findProductByCode(code)
        if (product != null) {
            throw ProductDuplicatedException.ofCode(code)
        }
    }
}
