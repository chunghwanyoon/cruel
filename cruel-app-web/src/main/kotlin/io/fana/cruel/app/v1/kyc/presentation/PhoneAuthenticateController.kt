package io.fana.cruel.app.v1.kyc.presentation

import io.fana.cruel.app.security.LoginUser
import io.fana.cruel.domain.kyc.application.CreatePhoneAuthenticationService
import io.fana.cruel.domain.kyc.application.GetPhoneAuthenticationService
import io.fana.cruel.domain.kyc.application.PhoneAuthenticationRequest
import io.fana.cruel.domain.kyc.application.VerifyPhoneAuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "KYC 휴대폰 본인인증 API", description = "KYC Phone Authentication API")
@RestController
@RequestMapping("/api/v1/kyc/phone")
class PhoneAuthenticateController(
    private val createPhoneAuthenticationService: CreatePhoneAuthenticationService,
    private val verifyPhoneAuthenticationService: VerifyPhoneAuthenticationService,
    private val getPhoneAuthenticationService: GetPhoneAuthenticationService,
) {
    /**
     * TODO:
     * api-gateway 에서 사용자의 휴대폰 인증이 완료되어있는지 확인
     * internal API를 따로 구성하거나, gRPC로 변경하면 좋을 것 같다.
     * 여러 서비스를 사용하려면 본인인증이 되어있어야 한다거나 하는 요구사항이 있다면,
     * api-gateway 단에서 처리한다고 가정하기 위함
     */
    @Operation(operationId = "isPhoneAuthenticationVerified", description = "휴대폰 본인인증 여부 확인")
    @ApiResponse(responseCode = "204", description = "휴대폰 본인인증 여부 확인")
    @GetMapping("/is-verified")
    fun isPhoneAuthenticationVerified(
        @RequestParam("userId") userId: Long,
    ) = getPhoneAuthenticationService.isPhoneAuthenticationVerified(userId)

    @Operation(operationId = "requestPhoneAuthentication", description = "휴대폰 본인인증 요청")
    @ApiResponse(description = "요청 성공", responseCode = "201")
    @PostMapping("/request")
    fun requestPhoneAuthentication(
        loginUser: LoginUser,
        @RequestBody request: PhoneAuthenticationRequest,
    ): PhoneAuthenticationResponse {
        return PhoneAuthenticationResponse.of(createPhoneAuthenticationService.createPhoneAuth(loginUser.id, request))
    }

    @Operation(operationId = "verifyPhoneAuthentication", description = "휴대폰 본인인증 검증 요청")
    @ApiResponse(description = "검증 성공", responseCode = "201")
    @PostMapping("/verify")
    fun verifyPhoneAuthentication(
        loginUser: LoginUser,
        @RequestBody request: PhoneAuthenticationVerifyRequest,
    ): PhoneAuthenticationResponse {
        return PhoneAuthenticationResponse.of(
            verifyPhoneAuthenticationService.verify(loginUser.id, request.verifyCode)
        )
    }
}
