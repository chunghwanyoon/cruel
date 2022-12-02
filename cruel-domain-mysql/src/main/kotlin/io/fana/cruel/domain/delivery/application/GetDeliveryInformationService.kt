package io.fana.cruel.domain.delivery.application

import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import io.fana.cruel.domain.delivery.exception.DeliveryInformationNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetDeliveryInformationService(
    private val deliveryInformationRepository: DeliveryInformationRepository,
) {
    fun getPrimaryDeliveryInformationByUserId(userId: Long): DeliveryInformation {
        return findPrimaryDeliveryInformationByUserId(userId)
            ?: throw DeliveryInformationNotFoundException.ofPrime(userId)
    }

    fun findPrimaryDeliveryInformationByUserId(userId: Long): DeliveryInformation? {
        return deliveryInformationRepository.findPrimeDeliveryInformationByUserId(userId)
    }

    fun getDeliveryInformationsByUserId(userId: Long): List<DeliveryInformation> {
        return deliveryInformationRepository.findDeliveryInformationsByUserId(userId)
    }
}
