package io.fana.cruel.domain.auth.application

import io.fana.cruel.domain.auth.exception.InvalidPasswordException
import io.fana.cruel.domain.auth.exception.InvalidUserNickNameException
import io.fana.cruel.domain.user.application.CreateUserService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SignUpService(
    private val getUserService: GetUserService,
    private val createUserService: CreateUserService,
) {
    fun signUp(nickName: String, plainPassword: String): User {
        requireValidNickNameLength(nickName)
        requireValidNickNamePattern(nickName)
        requireValidPasswordLength(plainPassword)
        TODO()
    }

    private fun requireValidNickNameLength(nickName: String) {
        if (nickName.length < MIN_NICK_NAME_LENGTH || nickName.length > MAX_NICK_NAME_LENGTH) {
            throw InvalidUserNickNameException.ofSize()
        }
    }

    private fun requireValidNickNamePattern(nickName: String) {
        if (!NICK_NAME_VALID_PATTERN.matches(nickName)) {
            throw InvalidUserNickNameException.ofPattern(nickName)
        }
    }

    private fun requireValidPasswordLength(plainPassword: String) {
        if (plainPassword.length < MIN_PASSWORD_LENGTH || plainPassword.length > MAX_PASSWORD_LENGTH) {
            throw InvalidPasswordException.ofSize()
        }
    }

    companion object {
        const val MIN_NICK_NAME_LENGTH = 3
        const val MAX_NICK_NAME_LENGTH = 16
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 32
        val NICK_NAME_VALID_PATTERN = Regex("^\\w+\$")
    }
}
