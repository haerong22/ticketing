package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage

interface PerformanceReader {

    fun getPerformanceList(pageable: Pageable) : WithPage<Performance>

    fun getPerformance(performanceId: Long): Performance

    fun getAvailableSeatList(performanceScheduleId: Long): List<Seat>
}