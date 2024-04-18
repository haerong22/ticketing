package io.haerong22.ticketing.interfaces.controller.reservation

class ReservationRequest {

    data class ReserveSeat(
        val userId: Long,
        val seatId: Long,
    )

    data class Payment(
        val userId: Long,
    )
}