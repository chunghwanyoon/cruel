package io.fana.cruel.domain.user.domain

import io.fana.cruel.core.entity.user.User

interface UserRepository {
    fun findUserById(id: Long): User?

    fun findUserByNickName(nickName: String): User?

    fun save(user: User): User
}
