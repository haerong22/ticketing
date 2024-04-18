package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import org.assertj.core.api.Assertions.assertThat
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
class ReservationServiceTest {

    @InjectMocks
    private lateinit var sut: ReservationService

    @Mock
    private lateinit var reservationStore: ReservationStore

    @Mock
    private lateinit var reservationReader: ReservationReader

    @Mock
    private lateinit var paymentValidator: PaymentValidator

    @Test
    fun `만료된 예약을 취소하고 seatId 리스트를 응답한다`() {
        // given
        val reservations = listOf(
            Reservation(
                reservationId = 1L,
                userId = 1L,
                seatId = 2L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            ),
        )

        given { reservationReader.getExpiredReservation() }.willAnswer { reservations }
        given { reservationStore.cancelExpiredReservation(listOf(1L)) }.willAnswer { listOf(2L) }

        // when
        val result = sut.cancelExpiredReservation()

        // then
        verify(reservationReader, times(1)).getExpiredReservation()
        verify(reservationStore, times(1)).cancelExpiredReservation(listOf(1L))

        assertThat(result).isEqualTo(listOf(2L))
    }
}