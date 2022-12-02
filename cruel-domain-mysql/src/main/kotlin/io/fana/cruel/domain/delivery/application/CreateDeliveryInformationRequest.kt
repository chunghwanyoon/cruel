package io.fana.cruel.domain.delivery.application

data class CreateDeliveryInformationRequest(
    val userId: Long,
    val address: String,
    val detailAddress: String,
    val isPrimary: Boolean,
)
