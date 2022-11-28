package io.fana.cruel.domain.product.domain

import org.springframework.stereotype.Repository

@Repository
interface ProductRepository {
    fun findAll(): List<Product>

    fun findProductById(productId: Long): Product?

    fun save(product: Product): Product
}
