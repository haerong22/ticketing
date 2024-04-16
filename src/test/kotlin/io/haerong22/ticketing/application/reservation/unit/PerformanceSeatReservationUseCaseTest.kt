package io.haerong22.ticketing.application.reservation.unit

import io.haerong22.ticketing.application.reservation.PerformanceSeatReservationUseCase
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.PerformanceReader
import io.haerong22.ticketing.domain.performance.PerformanceStore
import io.haerong22.ticketing.domain.performance.Seat
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.domain.reservation.ReservationStore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PerformanceSeatReservationUseCaseTest {

    @InjectMocks
    private lateinit var sut: PerformanceSeatReservationUseCase

    @Mock
    private lateinit var performanceReader: PerformanceReader

    @Mock
    private lateinit var performanceStore: PerformanceStore

    @Mock
    private lateinit var reservationStore: ReservationStore

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

        given(performanceReader.getSeatWithLock(seatId)).willReturn(seat)

        // when
        sut(command)

        // then
        verify(performanceReader, times(1)).getSeatWithLock(1L)
        verify(performanceStore, times(1)).save(any())
        verify(reservationStore, times(1)).save(any<Reservation>())
    }
}