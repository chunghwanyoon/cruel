package io.fana.cruel.domain.delivery.domain

import io.fana.cruel.domain.base.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Table(
    name = "delivery_informations",
    indexes = [
        Index(name = "idx_delivery_informations_user_id", columnList = "user_id")
    ]
)
@Entity
class DeliveryInformation(
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    address: String,
    detailAddress: String,
    isPrimary: Boolean = false,
) : BaseEntity() {
    @Column(name = "is_primary", nullable = false)
    var isPrimary: Boolean = isPrimary

    @Column(name = "address", nullable = false, length = 128)
    var address: String = address
        private set

    @Column(name = "detail_address", nullable = false, length = 128)
    var detailAddress: String = detailAddress
        private set

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set

    fun updateIsPrimary(primary: Boolean) {
        this.isPrimary = primary
    }
}
