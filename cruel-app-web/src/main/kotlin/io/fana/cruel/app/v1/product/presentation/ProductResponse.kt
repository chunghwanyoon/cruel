package io.fana.cruel.app.v1.product.presentation

import io.fana.cruel.domain.product.domain.Product

data class ProductResponse(
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    companion object {
        fun of(product: Product) = ProductResponse(
            name = product.name,
            price = product.price,
            imageUrl = product.imageUrl,
        )

        fun of(products: List<Product>) = products.map { of(it) }
    }
}
