package io.haerong22.ticketing.application.reservation.unit

import io.haerong22.ticketing.application.reservation.CancelExpiredReservationUseCase
import io.haerong22.ticketing.domain.performance.PerformanceService
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
class CancelExpiredReservationUseCaseTest {

    @InjectMocks
    private lateinit var sut: CancelExpiredReservationUseCase

    @Mock
    private lateinit var reservationService: ReservationService

    @Mock
    private lateinit var performanceService: PerformanceService

    @Test
    fun `만료된 예약 취소`() {
        // given
        val seatIds = listOf(1L, 2L)
        given { reservationService.cancelExpiredReservation() }.willAnswer { seatIds }

        // when
        sut()

        // then
        verify(reservationService, times(1)).cancelExpiredReservation()
        verify(performanceService, times(1)).openSeat(seatIds)
    }
}
