package io.fana.cruel.domain.user.domain

import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.auth.domain.LoginMethod
import io.fana.cruel.domain.user.exception.LoginMethodNotFoundException
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@EntityListeners(value = [AuditingEntityListener::class])
@Table(
    name = "users",
    indexes = [
        Index(name = "unq_users_nick_name", columnList = "nick_name", unique = true)
    ]
)
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "nick_name", length = 16, nullable = false)
    val nickName: String,
) {
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

    fun getLoginMethodByLoginTypeAndVersion(loginType: LoginType, version: Int): LoginMethod =
        this.loginMethods.find { it.loginType === loginType && it.version == version }
            ?: throw LoginMethodNotFoundException.of(loginType, version)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
