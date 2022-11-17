package io.fana.cruel.domain.order.domain

import io.fana.cruel.core.type.DelayStatus
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.domain.base.BaseEntity

import io.fana.cruel.domain.order.OrderTerm
import io.fana.cruel.domain.order.exception.InvalidOrderStatusException
import io.fana.cruel.domain.user.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ForeignKey
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(
    name = "orders",
    indexes = [
        Index(name = "idx_orders_user_id", columnList = "user_id"),
        Index(name = "idx_orders_status", columnList = "status"),
        Index(name = "idx_orders_delay_status", columnList = "delay_status"),
        Index(name = "idx_orders_interest_rate", columnList = "interest_rate"),
        Index(name = "idx_orders_term", columnList = "term"),
    ]
)
@Entity
class Order(
    @ManyToOne
    @JoinColumn(
        name = "user_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
        nullable = false
    )
    val user: User,

    @Column(name = "amount", nullable = false)
    val amount: Int,

    @Column(name = "interest_rate", nullable = false, precision = 6, scale = 3)
    val interestRate: BigDecimal,

    orderTerm: OrderTerm,

    @Column(name = "content", nullable = true, columnDefinition = "TEXT")
    val content: String? = null,
) : BaseEntity() {
    @Column(name = "term", nullable = false)
    var term: Int = orderTerm.term
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: OrderStatus = OrderStatus.CREATED
        private set

    @Enumerated(EnumType.STRING)
    @Column(name = "delay_status", nullable = false)
    var delayStatus: DelayStatus = DelayStatus.NORMAL
        private set

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set

    fun canceled(): Order {
        requireOrderStatus(OrderStatus.CREATED, OrderStatus.CANCELED)
        status = OrderStatus.CANCELED
        return this
    }

    fun activated(): Order {
        requireOrderStatus(OrderStatus.CREATED, OrderStatus.ACTIVATED)
        status = OrderStatus.ACTIVATED
        return this
    }

    fun completed(): Order {
        requireOrderStatus(OrderStatus.ACTIVATED, OrderStatus.COMPLETED)
        status = OrderStatus.COMPLETED
        return this
    }

    private fun requireOrderStatus(required: OrderStatus, targetStatus: OrderStatus) {
        if (status != required) {
            throw InvalidOrderStatusException.of(status, targetStatus)
        }
    }
}
