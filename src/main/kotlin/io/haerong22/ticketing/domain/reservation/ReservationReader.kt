package io.haerong22.ticketing.domain.reservation

interface ReservationReader {

    fun getReservationWithLock(reservationId: Long) : Reservation
}