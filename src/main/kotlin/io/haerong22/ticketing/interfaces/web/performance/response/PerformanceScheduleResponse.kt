package io.haerong22.ticketing.interfaces.web.performance.response

import java.time.LocalDateTime

class PerformanceScheduleListResponse(
    val title: String,
    val content: String,
    val date: List<Schedule>,
) {

    class Schedule(
        val performanceScheduleId: Long,
        val reservationAt: LocalDateTime,
        val startAt: LocalDateTime,
        val endAt: LocalDateTime,
    )
}

