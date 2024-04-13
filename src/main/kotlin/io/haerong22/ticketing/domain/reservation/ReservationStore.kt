package io.haerong22.ticketing.domain.reservation

interface ReservationStore {
    fun save(reservation: Reservation) : Reservation
}