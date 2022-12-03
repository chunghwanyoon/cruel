package io.fana.cruel.domain.delivery.application

data class DeliveryInformationRequest(
    val address: String,
    val detailAddress: String,
    val isPrimary: Boolean,
)
