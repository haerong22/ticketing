package io.haerong22.ticketing.interfaces.scheduler.reservation

import io.haerong22.ticketing.application.reservation.CancelExpiredReservationUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReservationScheduler(
    private val cancelExpiredReservationUseCase: CancelExpiredReservationUseCase,
) {

    @Scheduled(fixedDelay = 5000)
    fun cancelExpiredReservation() {
        cancelExpiredReservationUseCase()
    }
}
