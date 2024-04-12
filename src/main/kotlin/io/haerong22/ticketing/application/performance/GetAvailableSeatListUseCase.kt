package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.performance.PerformanceService
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.stereotype.Service

@Service
class GetAvailableSeatListUseCase(
    private val performanceService: PerformanceService,
) {

    operator fun invoke(performanceScheduleId: Long) : List<Seat> {
        return performanceService.getAvailableSeatList(performanceScheduleId)
    }
}