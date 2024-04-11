package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.performance.PerformanceSchedule
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "performance_schedule")
class PerformanceScheduleEntity(
    val performanceId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val reservationAt: LocalDateTime,
) : BaseEntity() {

    fun toDomain() : PerformanceSchedule {
        return PerformanceSchedule(
            performanceScheduleId = id!!,
            startAt = startAt,
            endAt = endAt,
            reservationAt = reservationAt,
        )
    }
}