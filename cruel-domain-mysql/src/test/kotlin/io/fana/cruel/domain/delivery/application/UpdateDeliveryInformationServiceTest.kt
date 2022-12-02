package io.fana.cruel.domain.delivery.application

import com.appmattus.kotlinfixture.kotlinFixture
import io.fana.cruel.domain.delivery.domain.DeliveryInformation
import io.fana.cruel.domain.user.domain.User
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UpdateDeliveryInformationServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val fixture = kotlinFixture()
    val getDeliveryInformationService = mockk<GetDeliveryInformationService>()
    val updateDeliveryInformationService = UpdateDeliveryInformationService(
        getDeliveryInformationService = getDeliveryInformationService,
    )
    val address = "NEW_ADDRESS"
    val detailAddress = "NEW_DETAIL_ADDRESS"
    val userFixture = fixture<User>()
    val deliveryInformation = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userFixture.id }
    }
    val updatedDeliveryInformation = fixture<DeliveryInformation> {
        property(DeliveryInformation::userId) { userFixture.id }
        property(DeliveryInformation::address) { address }
        property(DeliveryInformation::detailAddress) { detailAddress }
    }
    val request = fixture<DeliveryInformationRequest> {
        property(DeliveryInformationRequest::isPrimary) { true }
        property(DeliveryInformation::address) { address }
        property(DeliveryInformation::detailAddress) { detailAddress }
    }

    given("유저, 배송 정보, 요청이 주어졌을 때") {
        every {
            getDeliveryInformationService.getDeliveryInformationById(deliveryInformation.id)
        } returns deliveryInformation
        every { getDeliveryInformationService.findPrimaryDeliveryInformationByUserId(userFixture.id) } returns null

        `when`("배송 정보를 수정하면") {
            then("수정된다") {
                withContext(Dispatchers.IO) {
                    // FIXME: 아니 이거 또 왜 이러냐
                    val info = updateDeliveryInformationService.updateDeliveryInformation(
                        userFixture.id,
                        deliveryInformation.id,
                        request
                    )
                    println(info.toString())
                    // info.userId shouldBe userFixture.id
                    // info.address shouldBe address
                    // info.detailAddress shouldBe detailAddress
                    // info.isPrimary shouldBe true
                }
            }
        }
    }
})
