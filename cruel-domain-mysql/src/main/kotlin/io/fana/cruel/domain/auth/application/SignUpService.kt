package io.fana.cruel.domain.auth.application

import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.auth.domain.LoginMethod
import io.fana.cruel.domain.auth.exception.DuplicatedUserNickNameException
import io.fana.cruel.domain.auth.exception.InvalidPasswordException
import io.fana.cruel.domain.auth.exception.InvalidUserNickNameException
import io.fana.cruel.domain.crypt.application.CryptService
import io.fana.cruel.domain.user.application.CreateUserService
import io.fana.cruel.domain.user.application.GetUserService
import io.fana.cruel.domain.user.domain.User
import io.fana.cruel.domain.user.domain.User.Companion.DEFAULT_VERSION
import io.fana.cruel.domain.util.randomAlphaNumeric
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SignUpService(
    private val getUserService: GetUserService,
    private val createUserService: CreateUserService,
    private val cryptService: CryptService,
) {
    fun signUp(nickName: String, plainPassword: String): User {
        requireUniqueNickName(nickName)
        requireValidNickNameLength(nickName)
        requireValidNickNamePattern(nickName)
        requireValidPasswordLength(plainPassword)
        val user = createUserService.createUser(nickName)
        val randomSalt = randomSalt()
        val hashedPassword = cryptService.encrypt(plainPassword, randomSalt)
        user.addLoginMethod(createDefaultLoginMethod(randomSalt, hashedPassword))
        return user
    }

    private fun createDefaultLoginMethod(salt: String, hashedPassword: String): LoginMethod {
        return LoginMethod(
            loginType = LoginType.PASSWORD,
            hashedValue = hashedPassword,
            randomSalt = salt,
            version = DEFAULT_VERSION
        )
    }

    private fun requireUniqueNickName(nickName: String) {
        // TODO: validations for lower, uppercase uniqueness
        val user = getUserService.findUserByNickName(nickName)
        if (user !== null) {
            throw DuplicatedUserNickNameException.of(nickName)
        }
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

    private fun randomSalt(size: Int = 16) = randomAlphaNumeric(size)

    companion object {
        const val MIN_NICK_NAME_LENGTH = 3
        const val MAX_NICK_NAME_LENGTH = 16
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 32
        val NICK_NAME_VALID_PATTERN = Regex("^[a-zA-Z0-9_-]*$") // alpha numeric + underscore('_') + dash('-')
    }
}
