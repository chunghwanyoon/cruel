package io.fana.cruel.domain.user.application

import io.fana.cruel.domain.user.domain.User
import io.fana.cruel.domain.user.domain.UserRepository
import io.fana.cruel.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GetUserService(
    private val userRepository: UserRepository,
) {
    fun getUserById(userId: Long): User =
        userRepository.findUserById(userId) ?: throw UserNotFoundException.ofId(userId)

    fun getUserByNickName(nickName: String): User =
        userRepository.findUserByNickName(nickName) ?: throw UserNotFoundException.ofNickName(nickName)
}
