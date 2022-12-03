package io.fana.cruel.domain.delivery.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import io.fana.cruel.domain.delivery.exception.DeliveryInformationNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetDeliveryInformationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val deliveryInformationRepository = mockk<DeliveryInformationRepository>()
    val getDeliveryInformationService = GetDeliveryInformationService(
        deliveryInformationRepository = deliveryInformationRepository
    )
    val userId = 1L
    val primeDeliveryInfo = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userId }
        property(DeliveryInformation::isPrimary) { true }
    }
    val nonPrimeDeliveryInfo = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userId }
        property(DeliveryInformation::isPrimary) { false }
    }

    given("유저가 주어졌을 때") {
        `when`("기본 배송 정보를 조회하면") {
            every {
                deliveryInformationRepository.findPrimeDeliveryInformationByUserId(userId)
            } returns primeDeliveryInfo

            then("배송정보가 조회된다") {
                val information = withContext(Dispatchers.IO) {
                    getDeliveryInformationService.getPrimaryDeliveryInformationByUserId(userId)
                }
                information.userId shouldBe userId
                information.isPrimary shouldBe true
            }
        }

        `when`("기본 배송 정보가 존재하지 않으면") {
            every { deliveryInformationRepository.findPrimeDeliveryInformationByUserId(userId) } returns null
            then("예외가 발생한다") {
                shouldThrow<DeliveryInformationNotFoundException> {
                    getDeliveryInformationService.getPrimaryDeliveryInformationByUserId(userId)
                }
            }
        }

        `when`("유저의 배송 정보들을 조회하면") {
            every {
                deliveryInformationRepository.findDeliveryInformationsByUserId(userId)
            } returns listOf(primeDeliveryInfo, nonPrimeDeliveryInfo)

            then("배송 정보들이 조회된다") {
                val informations = withContext(Dispatchers.IO) {
                    getDeliveryInformationService.getDeliveryInformationsByUserId(userId)
                }
                informations.size shouldBe 2
                informations.filter { it.isPrimary }.size shouldBe 1
                informations.all { it.userId == userId } shouldBe true
            }
        }
    }
})
