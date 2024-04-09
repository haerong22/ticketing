package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.performance.PerformanceInfo
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "performance_info")
class PerformanceInfoEntity(
    val title: String,
    val content: String,
) : BaseEntity() {

    fun toDomain() : PerformanceInfo {
        return PerformanceInfo(
            performanceId = id!!,
            title = title,
            content = content,
        )
    }
}