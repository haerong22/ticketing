package io.haerong22.ticketing.interfaces.web.performance.response

import java.time.LocalDateTime

class PerformanceListResponse(
    val title: String,
    val content: String,
    val date: List<PerformanceDate>,
) {
}

class PerformanceDate(
    val performanceId: Long,
    val reservationAt: LocalDateTime,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)