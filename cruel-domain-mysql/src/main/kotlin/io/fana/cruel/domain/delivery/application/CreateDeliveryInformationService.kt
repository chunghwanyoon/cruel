package io.fana.cruel.domain.delivery.application

import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import io.fana.cruel.domain.user.application.GetUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateDeliveryInformationService(
    private val deliveryInformationRepository: DeliveryInformationRepository,
    private val getDeliveryInformationService: GetDeliveryInformationService,
    private val getUserService: GetUserService,
) {
    fun createDeliveryInformation(userId: Long, request: DeliveryInformationRequest): DeliveryInformation {
        val user = getUserService.getUserById(userId)
        val information = DeliveryInformation(
            userId = user.id,
            address = request.address,
            detailAddress = request.detailAddress,
            isPrimary = request.isPrimary,
        )
        disablePreviousPrimaryDeliveryInformation(user.id, request.isPrimary)
        return deliveryInformationRepository.save(information)
    }

    private fun disablePreviousPrimaryDeliveryInformation(userId: Long, isPrimaryChanged: Boolean) {
        if (isPrimaryChanged) {
            val previousPrimeInfo = getDeliveryInformationService.findPrimaryDeliveryInformationByUserId(userId)
            previousPrimeInfo?.disablePrimary()
        }
    }
}
