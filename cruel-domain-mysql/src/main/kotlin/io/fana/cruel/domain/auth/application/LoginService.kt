package io.fana.cruel.domain.auth.application

import io.fana.cruel.domain.auth.exception.LoginFailedException
import io.fana.cruel.domain.crypt.application.CryptService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class LoginService(
    private val getUserService: GetUserService,
    private val cryptService: CryptService,
) {
    fun login(nickName: String, plainPassword: String): User {
        val user = getUserService.getUserByNickName(nickName)
        validatePassword(user, plainPassword)
        return user
    }

    private fun validatePassword(user: User, plainPassword: String) {
        val hashedValue = cryptService.encrypt(plainPassword, user.getRandomSalt())
        if (hashedValue != user.hashedPassword()) {
            throw LoginFailedException()
        }
    }
}
