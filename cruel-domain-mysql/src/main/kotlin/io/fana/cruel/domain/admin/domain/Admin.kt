package io.fana.cruel.domain.admin.domain

import io.fana.cruel.domain.base.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "admins")
@Entity
class Admin(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    id: Long = 0L,
) : BaseEntity(id) {
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
}
