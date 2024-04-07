package io.haerong22.ticketing.interfaces.web.performance.response

class AvailableSeatListResponse(
    val availableSeat: List<Seat>,
    val totalSeats: Int,
    val remainingSeats: Int,
) {
}

class Seat(
    val seatId: Long,
    val seatNo: Int,
    val price: Int,
)