package io.haerong22.ticketing.application.reservation.unit

import io.haerong22.ticketing.application.reservation.ReservationPaymentUseCase
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationService
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.domain.user.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ReservationPaymentUseCaseTest {

    @InjectMocks
    private lateinit var sut: ReservationPaymentUseCase

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var reservationService: ReservationService

    @Test
    fun `예약을 결제할 수 있다`() {
        // given
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = LocalDateTime.now(),
        )
        val user = User(1L, "유저", UserPoint(0))

        val command = ReservationCommand.Pay(userId = 1L, reservationId = 1L)

        given { userService.getUserWithLock(1L) }.willAnswer { user }
        given { reservationService.getReservationWithLock(1L) }.willAnswer { reservation }

        // when
        sut(command)

        // then
        verify(userService, times(1)).getUserWithLock(1L)
        verify(reservationService, times(1)).getReservationWithLock(1L)
    }
}
