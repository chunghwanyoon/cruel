package io.fana.cruel.domain.user.domain

import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.auth.domain.LoginMethod
import io.fana.cruel.domain.base.BaseEntity
import io.fana.cruel.domain.user.exception.LoginMethodNotFoundException
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(
    name = "users",
    indexes = [
        Index(name = "unq_users_nick_name", columnList = "nick_name", unique = true)
    ]
)
@Entity
class User(
    @Column(name = "nick_name", length = 16, nullable = false)
    val nickName: String,
) : BaseEntity() {
    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    @JoinColumn(
        name = "user_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        nullable = false
    )
    var loginMethods: MutableList<LoginMethod> = mutableListOf()

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        private set

    fun addLoginMethod(loginMethod: LoginMethod) =
        this.loginMethods.add(loginMethod)

    fun getRandomSalt() = getLoginMethodByLoginTypeAndVersion(LoginType.PASSWORD).randomSalt

    fun hashedPassword() = getLoginMethodByLoginTypeAndVersion(LoginType.PASSWORD).hashedValue

    private fun getLoginMethodByLoginTypeAndVersion(loginType: LoginType, version: Int = DEFAULT_VERSION): LoginMethod =
        this.loginMethods.find { it.loginType === loginType && it.version == version }
            ?: throw LoginMethodNotFoundException.of(loginType, version)

    companion object {
        const val DEFAULT_VERSION = 1
    }
}
