package io.fana.cruel.domain.delivery.domain

import org.springframework.stereotype.Repository

@Repository
interface DeliveryInformationRepository {
    fun findPrimeDeliveryInformationByUserId(userId: Long): DeliveryInformation?

    fun findDeliveryInformationsByUserId(userId: Long): List<DeliveryInformation>

    fun save(information: DeliveryInformation): DeliveryInformation
}
