package io.fana.cruel.app.v1.user.presentation

import io.fana.cruel.domain.delivery.application.GetDeliveryInformationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 배송 정보 API", description = "User Delivery Information API")
@RestController
@RequestMapping("/api/v1/users")
class UserDeliveryInformationController(
    private val getDeliveryInformationService: GetDeliveryInformationService,
) {
    @Operation(operationId = "getDeliveryInformations", description = "배송 정보 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    @GetMapping("/{userId}/delivery-informations")
    fun getDeliveryInformations(
        @PathVariable("userId") userId: Long,
    ): List<DeliveryInformationResponse> {
        return DeliveryInformationResponse.of(
            getDeliveryInformationService.getDeliveryInformationsByUserId(userId)
        )
    }
}
