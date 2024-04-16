package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.reservation.ReservationService
import io.haerong22.ticketing.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ReservationPaymentUseCase(
    private val userService: UserService,
    private val reservationService: ReservationService,
) {

    operator fun invoke(command: ReservationCommand.Pay) {
        val reservation = reservationService.getReservationWithLock(command.reservationId)
        val user = userService.getUserWithLock(command.userId)

        reservationService.pay(user, reservation)
        userService.usePoint(user, reservation.price)
    }
}