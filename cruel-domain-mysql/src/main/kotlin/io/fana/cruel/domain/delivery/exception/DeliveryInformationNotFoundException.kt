package io.fana.cruel.domain.delivery.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class DeliveryInformationNotFoundException(
    message: String?,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.DELIVERY_INFORMATION_NOT_FOUND, message, cause) {
    companion object {
        fun ofPrime(userId: Long) =
            DeliveryInformationNotFoundException("유저의 기본 배송지 정보를 찾을 수 없습니다. userId: $userId")

        fun ofId(deliveryInformationId: Long) =
            DeliveryInformationNotFoundException("배송정보를 찾을 수 없습니다. deliveryInformationId: $deliveryInformationId")
    }
}
