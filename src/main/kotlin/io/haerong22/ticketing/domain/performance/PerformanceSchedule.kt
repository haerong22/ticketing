package io.haerong22.ticketing.domain.performance

import java.time.LocalDateTime

data class PerformanceSchedule(
    val performanceScheduleId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val reservationAt: LocalDateTime,
)
