package io.fana.cruel.domain.schedule.domain

import io.fana.cruel.domain.base.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Table(
    name = "return_schedules",
    indexes = [
        Index(name = "idx_return_schedules_order_id", columnList = "order_id"),
        Index(name = "idx_return_schedules_principal", columnList = "principal"),
        Index(name = "idx_return_schedules_interest", columnList = "interest"),
        Index(name = "idx_return_schedules_scheduled_at", columnList = "scheduled_at"),
        Index(name = "idx_return_schedules_is_returned", columnList = "is_returned"),
    ]
)
@Entity
class ReturnSchedule(
    @Column(name = "order_id", nullable = false)
    val orderId: Long,

    @Column(name = "principal", nullable = false)
    val principal: Int,

    @Column(name = "interest", nullable = false)
    val interest: Int,

    @Column(name = "seq_id", nullable = false)
    val seqId: Int,

    scheduledAt: LocalDateTime,
) : BaseEntity() {
    @Column(name = "total_amount", nullable = false)
    val totalAmount: Int = principal + interest

    @Column(name = "is_returned", nullable = false)
    val isReturned: Boolean = false

    @Column(name = "scheduled_at", nullable = false)
    var scheduledAt: LocalDateTime = scheduledAt

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set
}
