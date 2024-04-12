package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.Seat
import org.springframework.stereotype.Service

@Service
class GetAvailableSeatListUseCase(
    private val performanceReader: PerformanceReader,
) {

    operator fun invoke(performanceScheduleId: Long) : List<Seat> {
        return performanceReader.getAvailableSeatList(performanceScheduleId)
    }
}