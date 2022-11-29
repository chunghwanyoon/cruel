package io.fana.cruel.domain.product.application

data class UpdateProductRequest(
    val name: String,
    val code: String,
    val price: Int,
    val imageUrl: String,
    val isActivated: Boolean,
)
