package io.fana.cruel.domain.kyc.domain

import io.fana.cruel.domain.base.BaseEntity
import io.fana.cruel.domain.type.MobileCode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Table(
    name = "phone_authentications",
    indexes = [
        Index(name = "unq_phone_authentications_phone_number", columnList = "phone_number", unique = true)
    ]
)
@Entity
class PhoneAuthentication(
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    /**
     * QUESTION: 본인인증 후 개명된다면 어떻게 되는거임?
     */
    @Column(name = "name", nullable = false)
    val name: String,

    unverifiedPhoneNumber: String,
    unverifiedMobileCode: MobileCode,
) : BaseEntity() {
    @Column(name = "phone_number", nullable = true, unique = true)
    var phoneNumber: String? = null
        private set

    @Column(name = "mobile_code", nullable = true)
    var mobileCode: MobileCode? = null
        private set

    @Column(name = "unverified_phone_number", nullable = false)
    var unverifiedPhoneNumber: String = unverifiedPhoneNumber

    @Column(name = "unverified_mobile_code", nullable = false)
    var unverifiedMobileCode: MobileCode = unverifiedMobileCode

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set

    fun authenticate(): PhoneAuthentication {
        phoneNumber = unverifiedPhoneNumber
        mobileCode = unverifiedMobileCode
        return this
    }

    fun updateUnverified(phoneNumber: String, mobileCode: MobileCode): PhoneAuthentication {
        unverifiedPhoneNumber = phoneNumber
        unverifiedMobileCode = mobileCode
        return this
    }

    fun isAuthenticated(): Boolean {
        return phoneNumber != null
    }
}
