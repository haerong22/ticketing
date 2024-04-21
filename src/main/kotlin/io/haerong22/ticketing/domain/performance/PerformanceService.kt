package io.haerong22.ticketing.domain.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.domain.performance.PerformanceResponseCode.PERFORMANCE_NOT_FOUND
import io.haerong22.ticketing.domain.performance.PerformanceResponseCode.PERFORMANCE_SEAT_NOT_FOUND
import org.springframework.stereotype.Service

@Service
class PerformanceService(
    private val performanceReader: PerformanceReader,
    private val performanceStore: PerformanceStore,
) {

    fun getPerformanceList(pageable: Pageable): WithPage<Performance> {
        return performanceReader.getPerformanceList(pageable)
    }

    fun getPerformance(performanceId: Long): Performance {
        return performanceReader.getPerformance(performanceId)
            ?: throw PerformanceException(PERFORMANCE_NOT_FOUND)
    }

    fun getAvailableSeatList(performanceScheduleId: Long): List<Seat> {
        return performanceReader.getAvailableSeatList(performanceScheduleId)
    }

    fun getSeatWithLock(seatId: Long): Seat {
        return performanceReader.getSeatWithLock(seatId)
            ?: throw PerformanceException(PERFORMANCE_SEAT_NOT_FOUND)
    }

    fun reserve(seat: Seat) {
        val newSeat = seat.reserve()
        performanceStore.save(newSeat)
    }

    fun openSeat(seatIds: List<Long>) {
        performanceStore.openSeat(seatIds)
    }
}
