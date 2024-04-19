package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.performance.PerformanceService
import io.haerong22.ticketing.domain.reservation.ReservationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PerformanceSeatReservationUseCase(
    private val performanceService: PerformanceService,
    private val reservationService: ReservationService,
) {

    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = performanceService.getSeatWithLock(command.seatId)
        performanceService.reserve(seat)
        reservationService.reserve(command.userId, command.seatId, seat.price)
    }
}