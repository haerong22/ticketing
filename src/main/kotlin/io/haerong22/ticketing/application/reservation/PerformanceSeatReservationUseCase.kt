package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.PerformanceStore
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PerformanceSeatReservationUseCase(
    private val performanceReader: PerformanceReader,
    private val performanceStore: PerformanceStore,
    private val reservationStore: ReservationStore,
) {

    operator fun invoke(command: ReservationCommand.Reserve) {
        val seat = performanceReader.getSeatWithLock(command.seatId)
        val newSeat = seat.reserve()
        performanceStore.save(newSeat)

        val reservation = Reservation.reserve(command.userId, command.seatId)
        reservationStore.save(reservation)
    }
}