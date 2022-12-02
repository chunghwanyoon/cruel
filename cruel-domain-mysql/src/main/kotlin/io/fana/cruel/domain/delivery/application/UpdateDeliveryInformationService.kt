package io.fana.cruel.domain.delivery.application

import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class UpdateDeliveryInformationService(
    private val getDeliveryInformationService: GetDeliveryInformationService,
) {
    fun updateDeliveryInformation(
        userId: Long,
        deliveryInformationId: Long,
        request: DeliveryInformationRequest,
    ): DeliveryInformation {
        val information = getDeliveryInformationService.getDeliveryInformationById(deliveryInformationId)
        disablePreviousPrimaryDeliveryInformation(userId, request.isPrimary)
        return information.updateInfo(
            address = request.address,
            detailAddress = request.detailAddress,
            isPrimary = request.isPrimary,
        )
    }

    private fun disablePreviousPrimaryDeliveryInformation(userId: Long, isPrimaryChanged: Boolean) {
        if (isPrimaryChanged) {
            val previousPrimeInfo = getDeliveryInformationService.findPrimaryDeliveryInformationByUserId(userId)
            previousPrimeInfo?.disablePrimary()
        }
    }
}
