package io.fana.cruel.domain.delivery.application

import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DeleteDeliveryInformationService(
    private val deliveryInformationRepository: DeliveryInformationRepository,
) {
    fun deleteById(deliveryInformationId: Long) {
        deliveryInformationRepository.deleteDeliveryInformationById(deliveryInformationId)
    }
}
