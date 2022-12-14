package io.fana.cruel.domain.product.infra

import io.fana.cruel.domain.product.domain.Product
import io.fana.cruel.domain.product.domain.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaProductRepository : ProductRepository, JpaRepository<Product, Long> {
    override fun findAll(): List<Product>

    override fun findProductsByIsActivated(isActivated: Boolean): List<Product>

    override fun findProductById(productId: Long): Product?

    override fun findProductByCode(code: String): Product?

    override fun save(product: Product): Product

    override fun deleteProductById(productId: Long)
}
