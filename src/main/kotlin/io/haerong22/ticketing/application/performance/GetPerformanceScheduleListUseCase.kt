package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceService
import org.springframework.stereotype.Service

@Service
class GetPerformanceScheduleListUseCase(
    private val performanceService: PerformanceService,
) {

    operator fun invoke(performanceId: Long): Performance {
        return performanceService.getPerformanceScheduleList(performanceId)
    }
}