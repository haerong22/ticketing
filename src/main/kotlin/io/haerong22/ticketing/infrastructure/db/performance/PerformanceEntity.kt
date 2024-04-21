package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "performance")
class PerformanceEntity(
    val title: String,
    val content: String,
) : BaseEntity() {

    fun toDomain(): Performance {
        return Performance(
            performanceId = id!!,
            title = title,
            content = content,
        )
    }
}
