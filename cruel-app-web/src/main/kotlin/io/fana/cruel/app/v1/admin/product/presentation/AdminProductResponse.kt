package io.fana.cruel.app.v1.admin.product.presentation

import io.fana.cruel.domain.product.domain.Product
import java.time.LocalDateTime

data class AdminProductResponse(
    val name: String,
    val code: String,
    val price: Int,
    val imageUrl: String,
    val isActivated: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(product: Product) = AdminProductResponse(
            name = product.name,
            code = product.code,
            price = product.price,
            imageUrl = product.imageUrl,
            isActivated = product.isActivated,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt,
        )

        fun of(products: List<Product>) = products.map { of(it) }
    }
}
