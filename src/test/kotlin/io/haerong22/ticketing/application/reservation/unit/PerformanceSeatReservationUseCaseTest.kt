package io.haerong22.ticketing.application.reservation.unit

import io.haerong22.ticketing.application.reservation.PerformanceSeatReservationUseCase
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.PerformanceService
import io.haerong22.ticketing.domain.performance.Seat
import io.haerong22.ticketing.domain.reservation.ReservationService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PerformanceSeatReservationUseCaseTest {

    @InjectMocks
    private lateinit var sut: PerformanceSeatReservationUseCase

    @Mock
    private lateinit var performanceService: PerformanceService

    @Mock
    private lateinit var reservationService: ReservationService

    @Test
    fun `좌석을 예약할 수 있다`() {
        // given
        val userId = 1L
        val seatId = 1L
        val command = ReservationCommand.Reserve(userId, seatId)
        val seat = Seat(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            status = SeatStatus.OPEN
        )

        given(performanceService.getSeatWithLock(seatId)).willReturn(seat)

        // when
        sut(command)

        // then
        verify(performanceService, times(1)).getSeatWithLock(1L)
        verify(performanceService, times(1)).reserve(seat)
        verify(reservationService, times(1)).reserve(1L, 1L, 10000)
    }
}
