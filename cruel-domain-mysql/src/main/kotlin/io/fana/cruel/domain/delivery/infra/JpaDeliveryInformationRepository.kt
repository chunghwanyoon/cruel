package io.fana.cruel.domain.delivery.infra

import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaDeliveryInformationRepository : DeliveryInformationRepository, JpaRepository<DeliveryInformation, Long> {
    @Query(
        """
            SELECT di
            FROM DeliveryInformation di
            WHERE di.userId = :userId
            AND di.isPrimary IS TRUE
        """
    )
    override fun findPrimeDeliveryInformationByUserId(userId: Long): DeliveryInformation?

    override fun findDeliveryInformationsByUserId(userId: Long): List<DeliveryInformation>

    override fun findDeliveryInformationById(deliveryInformationId: Long): DeliveryInformation?

    override fun deleteDeliveryInformationById(deliveryInformationId: Long)

    override fun save(information: DeliveryInformation): DeliveryInformation
}
