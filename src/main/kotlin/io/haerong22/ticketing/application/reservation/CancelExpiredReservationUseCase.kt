package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.domain.performance.PerformanceService
import io.haerong22.ticketing.domain.reservation.ReservationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CancelExpiredReservationUseCase(
    private val reservationService: ReservationService,
    private val performanceService: PerformanceService,
) {

    operator fun invoke() {
        val seatIds = reservationService.cancelExpiredReservation()
        performanceService.openSeat(seatIds)
    }
}
