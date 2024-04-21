package io.haerong22.ticketing.domain.performance

data class Performance(
    val performanceId: Long,
    val title: String,
    val content: String,

    val schedules: List<PerformanceSchedule>? = null
)
