package io.haerong22.ticketing.interfaces.web.reservation

import io.haerong22.ticketing.application.reservation.PerformanceSeatReservationUseCase
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.interfaces.web.CommonResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservations")
class ReservationController(
    private val performanceSeatReservationUseCase: PerformanceSeatReservationUseCase,
) {

    @PostMapping
    fun reserveSeat(
        @RequestHeader("wq-token") token: String,
        @RequestBody request: ReservationRequest.ReserveSeat,
    ): CommonResponse<Unit> {
        val command = ReservationCommand.Reserve(request.userId, request.seatId)
        performanceSeatReservationUseCase(command)
        return CommonResponse.ok()
    }
}