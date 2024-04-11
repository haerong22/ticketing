package io.haerong22.ticketing.interfaces.web.performance

class PerformanceRequest {

    data class Payment(
        val reservationId: Long,
    )

    data class ReserveSeat(
        val userId: Long,
        val seatNo: Int,
    )
}