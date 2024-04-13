package io.haerong22.ticketing.application.reservation.command

class ReservationCommand {

    data class Reserve(
        val userId: Long,
        val seatId: Long,
    )
}