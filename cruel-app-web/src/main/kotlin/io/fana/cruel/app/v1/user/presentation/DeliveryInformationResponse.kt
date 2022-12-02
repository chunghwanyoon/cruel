package io.fana.cruel.app.v1.user.presentation

import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import java.time.LocalDateTime

data class DeliveryInformationResponse(
    val userId: Long,
    val address: String,
    val detailAddress: String,
    val isPrimary: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun of(deliveryInformation: DeliveryInformation) =
            DeliveryInformationResponse(
                userId = deliveryInformation.userId,
                address = deliveryInformation.address,
                detailAddress = deliveryInformation.detailAddress,
                isPrimary = deliveryInformation.isPrimary,
                createdAt = deliveryInformation.createdAt,
                updatedAt = deliveryInformation.updatedAt,
            )

        fun of(deliveryInformations: List<DeliveryInformation>) = deliveryInformations.map { of(it) }
    }
}
