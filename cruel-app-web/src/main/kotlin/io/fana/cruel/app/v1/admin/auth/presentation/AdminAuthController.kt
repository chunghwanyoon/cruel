package io.fana.cruel.app.v1.admin.auth.presentation

import io.fana.cruel.app.v1.auth.application.LoginAdminService
import io.fana.cruel.app.v1.auth.presentation.LoginRequest
import io.fana.cruel.app.v1.auth.presentation.LoginResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "어드민 인증", description = "어드민을 인증하기 위한 API")
@RestController
@RequestMapping("/api/v1/admin/auth")
class AdminAuthController(
    private val loginAdminService: LoginAdminService,
) {
    /**
     * TODO:
     * api-gateway가 앞에 있다고 가정하고
     * 타 presentation layer에서 /api/v1/admin path로 들어오는 토큰에 대해 따로 검증 하지 않음
     */
    @Operation(operationId = "loginAdmin", description = "어드민 로그인")
    @ApiResponse(responseCode = "201", description = "로그인 성공")
    @PostMapping("/login")
    fun loginAdmin(
        @RequestBody request: LoginRequest,
    ): LoginResponse {
        return loginAdminService.login(request)
    }
}
