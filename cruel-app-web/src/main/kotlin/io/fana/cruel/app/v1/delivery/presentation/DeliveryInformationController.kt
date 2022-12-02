package io.fana.cruel.app.v1.delivery.presentation

import io.fana.cruel.app.security.LoginUser
import io.fana.cruel.app.v1.user.presentation.DeliveryInformationResponse
import io.fana.cruel.domain.delivery.application.CreateDeliveryInformationService
import io.fana.cruel.domain.delivery.application.DeleteDeliveryInformationService
import io.fana.cruel.domain.delivery.application.DeliveryInformationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "배송 정보 API", description = "Delivery Information API")
@RestController
@RequestMapping("/api/v1/delivery-informations")
class DeliveryInformationController(
    private val createDeliveryInformationService: CreateDeliveryInformationService,
    private val deleteDeliveryInformationService: DeleteDeliveryInformationService,
) {
    @Operation(operationId = "createDeliveryInformation", description = "배송 정보 생성")
    @ApiResponse(description = "생성 성공", responseCode = "201")
    @PostMapping
    fun createDeliveryInformation(
        @Parameter(hidden = true)
        loginUser: LoginUser,
        @RequestBody request: DeliveryInformationRequest,
    ): DeliveryInformationResponse {
        return DeliveryInformationResponse.of(
            createDeliveryInformationService.createDeliveryInformation(loginUser.id, request)
        )
    }

    @Operation(operationId = "deleteDeliveryInformation", description = "배송 정보 삭제")
    @ApiResponse(description = "성공", responseCode = "204")
    @PutMapping("/{deliveryInformationId}")
    fun deleteDeliveryInformation(
        @Parameter(hidden = true)
        loginUser: LoginUser,
        @PathVariable("deliveryInformationId") deliveryInformationId: Long,
    ) {
        deleteDeliveryInformationService.deleteById(deliveryInformationId)
    }
}
