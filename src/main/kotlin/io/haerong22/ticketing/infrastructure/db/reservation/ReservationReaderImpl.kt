package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationReader
import org.springframework.stereotype.Repository

@Repository
class ReservationReaderImpl(
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationReader {

    override fun getReservationWithLock(reservationId: Long): Reservation {
        TODO("Not yet implemented")
    }
}