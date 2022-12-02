package io.fana.cruel.domain.delivery.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.delivery.domain.DeliveryInformationRepository
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreateDeliveryInformationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val deliveryInformationRepository = mockk<DeliveryInformationRepository>()
    val getDeliveryInformationService = mockk<GetDeliveryInformationService>()
    val getUserService = mockk<GetUserService>()
    val createDeliveryInformationService = CreateDeliveryInformationService(
        deliveryInformationRepository = deliveryInformationRepository,
        getDeliveryInformationService = getDeliveryInformationService,
        getUserService = getUserService,
    )
    val userFixture = fixture<User>()
    val primeDeliveryInfo = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userFixture.id }
        property(DeliveryInformation::isPrimary) { true }
    }
    val previousPrimeDeliveryInfo = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userFixture.id }
        property(DeliveryInformation::isPrimary) { true }
    }
    val request = fixture<DeliveryInformationRequest> {
        property(DeliveryInformationRequest::isPrimary) { true }
    }

    given("배송 생성 정보가 주어졌을 때") {
        every { getUserService.getUserById(userFixture.id) } returns userFixture
        every { deliveryInformationRepository.save(primeDeliveryInfo) } returns primeDeliveryInfo
        every {
            getDeliveryInformationService.findPrimaryDeliveryInformationByUserId(userFixture.id)
        } returns previousPrimeDeliveryInfo

        `when`("배송 정보를 생성하려고 하면") {
            then("배송 정보가 생성된다") {
                val information = withContext(Dispatchers.IO) {
                    createDeliveryInformationService.createDeliveryInformation(userFixture.id, request)
                }
                information.userId shouldBe userFixture.id
                information.isPrimary shouldBe true
                previousPrimeDeliveryInfo.isPrimary shouldBe false
            }
        }
    }
})
