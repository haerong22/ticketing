package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationStore
import org.springframework.stereotype.Repository

@Repository
class ReservationStoreImpl : ReservationStore {

    override fun save(reservation: Reservation): Reservation {
        TODO("Not yet implemented")
    }
}