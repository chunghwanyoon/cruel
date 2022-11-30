package io.fana.cruel.domain.product.application

data class CreateProductRequest(
    val name: String,
    val code: String,
    val price: Int,
    val imageUrl: String,
)
