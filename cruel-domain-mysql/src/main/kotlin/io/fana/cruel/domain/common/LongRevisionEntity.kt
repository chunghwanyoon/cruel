package io.fana.cruel.domain.common

import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp
import java.io.Serializable
import java.text.DateFormat
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

/**
 * PK를 Long 타입으로 사용하고 있으므로
 * envers의 REV 타입을 변경하기 위해 사용한다.
 */
@Table(name = "REVINFO")
@RevisionEntity
@Entity
class LongRevisionEntity() : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "REV")
    var id: Long? = null

    @RevisionTimestamp
    @Column(name = "REVTSTMP")
    var timestamp: Long? = null

    constructor(id: Long, timestamp: Long) : this() {
        this.id = id
        this.timestamp = timestamp
    }

    @Transient
    fun getRevisionDate() = timestamp?.let { Date(it) }

    override fun toString(): String =
        "LongRevisionEntity(id = $id, revisionDate = ${DateFormat.getDateTimeInstance().format(getRevisionDate())}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LongRevisionEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
