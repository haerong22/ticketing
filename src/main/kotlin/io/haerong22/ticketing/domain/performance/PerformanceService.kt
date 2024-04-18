package io.haerong22.ticketing.domain.performance

import org.springframework.stereotype.Service

@Service
class PerformanceService(
    private val performanceStore: PerformanceStore,
) {

    fun openSeat(seatIds: List<Long>) {
        performanceStore.openSeat(seatIds)
    }
}