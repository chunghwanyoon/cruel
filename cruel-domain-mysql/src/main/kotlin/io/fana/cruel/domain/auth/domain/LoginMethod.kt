package io.fana.cruel.domain.auth.domain

import io.fana.cruel.core.type.LoginType
import io.fana.cruel.domain.base.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Index
import javax.persistence.Table

@EntityListeners(value = [AuditingEntityListener::class])
@Table(
    name = "login_methods",
    indexes = [
        Index(name = "idx_login_methods_user_id", columnList = "user_id"),
        Index(name = "idx_login_methods_login_type", columnList = "login_type"),
        Index(name = "idx_login_methods_version", columnList = "version"),
    ]
)
@Entity
class LoginMethod(
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    val loginType: LoginType,

    @Column(name = "hashed_value", nullable = false)
    val hashedValue: String,

    @Column(name = "random_salt", nullable = false)
    val randomSalt: String,

    @Column(name = "version", nullable = false)
    val version: Int = 1,
) : BaseEntity() {
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set
}
