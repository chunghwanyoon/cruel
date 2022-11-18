package io.fana.cruel.app.v1.auth.application

import io.fana.cruel.app.security.JwtProvider
import io.fana.cruel.app.security.UserRole
import io.fana.cruel.app.v1.auth.presentation.LoginRequest
import io.fana.cruel.app.v1.auth.presentation.LoginResponse
import io.fana.cruel.domain.auth.exception.LoginFailedException
import io.fana.cruel.domain.crypt.application.CryptService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val getUserService: GetUserService,
    private val cryptService: CryptService,
    private val jwtProvider: JwtProvider,
) {
    fun login(request: LoginRequest): LoginResponse {
        val user = getUserService.getUserByNickName(request.nickName)
        validatePassword(user, request.plainPassword)
        val token = jwtProvider.createToken(user.id, UserRole.COMMON)
        return LoginResponse(user.id, user.nickName, token)
    }

    private fun validatePassword(user: User, plainPassword: String) {
        val hashedValue = cryptService.encrypt(plainPassword, user.getRandomSalt())
        if (hashedValue != user.hashedPassword()) {
            throw LoginFailedException()
        }
    }
}
