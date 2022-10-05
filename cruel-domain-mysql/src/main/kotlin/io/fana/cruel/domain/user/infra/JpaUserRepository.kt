package io.fana.cruel.domain.user.infra

import io.fana.cruel.domain.user.domain.User
import io.fana.cruel.domain.user.domain.UserRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : UserRepository, JpaRepository<User, Long> {
    override fun findUserById(id: Long): User?

    override fun findUserByNickName(nickName: String): User?

    override fun save(user: User): User
}
