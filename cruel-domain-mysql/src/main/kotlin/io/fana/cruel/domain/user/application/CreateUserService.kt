package io.fana.cruel.domain.user.application

import io.fana.cruel.domain.user.domain.User
import io.fana.cruel.domain.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateUserService(
    private val userRepository: UserRepository,
) {
    fun createUser(nickName: String): User = userRepository.save(User(nickName))
}
