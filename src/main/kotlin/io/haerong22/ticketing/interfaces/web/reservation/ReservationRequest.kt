package io.haerong22.ticketing.interfaces.web.reservation

class ReservationRequest {

    data class ReserveSeat(
        val userId: Long,
        val seatId: Long,
    )
}