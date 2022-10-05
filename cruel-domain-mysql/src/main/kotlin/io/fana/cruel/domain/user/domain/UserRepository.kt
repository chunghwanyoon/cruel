package io.fana.cruel.domain.user.domain

interface UserRepository {
    fun findUserById(id: Long): User?

    fun findUserByNickName(nickName: String): User?

    fun save(user: User): User
}
