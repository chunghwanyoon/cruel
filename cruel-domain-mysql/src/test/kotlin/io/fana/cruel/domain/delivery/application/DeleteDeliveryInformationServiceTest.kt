package io.fana.cruel.domain.delivery.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

internal class DeleteDeliveryInformationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val deliveryInformationRepository = mockk<DeliveryInformationRepository>()
    val deleteDeliveryInformationService = DeleteDeliveryInformationService(
        deliveryInformationRepository = deliveryInformationRepository,
    )
    val deliveryInformation = fixture<DeliveryInformation>()

    given("배송 정보가 주어졌을 때") {
        every { deliveryInformationRepository.deleteDeliveryInformationById(deliveryInformation.id) } returns Unit

        `when`("배송 정보를 삭제하면") {
            then("삭제된다") {
                shouldNotThrowAny {
                    deleteDeliveryInformationService.deleteById(deliveryInformation.id)
                }
            }
        }
    }
})
