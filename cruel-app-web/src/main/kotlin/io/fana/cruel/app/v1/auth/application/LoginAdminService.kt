package io.fana.cruel.app.v1.auth.application

import io.fana.cruel.app.security.JwtProvider
import io.fana.cruel.app.security.UserRole
import io.fana.cruel.app.v1.auth.presentation.LoginRequest
import io.fana.cruel.app.v1.auth.presentation.LoginResponse
import io.fana.cruel.domain.admin.application.GetAdminService
import io.fana.cruel.domain.admin.domain.Admin
import io.fana.cruel.domain.admin.exception.AdminDeletedException
import io.fana.cruel.domain.user.application.GetUserService
import org.springframework.stereotype.Service

@Service
class LoginAdminService(
    private val getAdminService: GetAdminService,
    private val getUserService: GetUserService,
    private val jwtProvider: JwtProvider,
) {
    fun login(request: LoginRequest): LoginResponse {
        val user = getUserService.getUserByNickName(request.nickName)
        val admin = getAdminService.getAdminByUserId(user.id)
        requireAdminNotDeleted(admin)
        val token = jwtProvider.createToken(user.id, UserRole.ADMIN)
        return LoginResponse(user.id, user.nickName, token)
    }

    private fun requireAdminNotDeleted(admin: Admin) {
        if (admin.deletedAt != null) {
            throw AdminDeletedException.of(admin.id, admin.name)
        }
    }
}
