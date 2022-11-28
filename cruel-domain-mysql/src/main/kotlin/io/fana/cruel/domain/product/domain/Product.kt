package io.fana.cruel.domain.product.domain

import io.fana.cruel.domain.base.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Table(
    name = "products",
    indexes = [
        Index(name = "unq_products_code", columnList = "code", unique = true),
        Index(name = "unq_products_name", columnList = "name", unique = true),
        Index(name = "idx_products_is_activated", columnList = "is_activated"),
    ]
)
@Entity
class Product(
    name: String,
    code: String,
    price: Int,
) : BaseEntity() {
    @Column(name = "name", nullable = false, unique = true)
    var name: String = name

    @Column(name = "code", nullable = false, unique = true)
    var code: String = code

    @Column(name = "price", nullable = false)
    var price: Int = price

    @Column(name = "is_activated", nullable = false)
    var isActivated: Boolean = true

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set
}
