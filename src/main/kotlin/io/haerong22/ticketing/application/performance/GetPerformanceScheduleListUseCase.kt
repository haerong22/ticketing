package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.performance.Performance
import io.haerong22.ticketing.domain.performance.PerformanceReader
import org.springframework.stereotype.Service

@Service
class GetPerformanceScheduleListUseCase(
    private val performanceReader: PerformanceReader,
) {

    operator fun invoke(performanceId: Long): Performance {
        return performanceReader.getPerformance(performanceId)
    }
}