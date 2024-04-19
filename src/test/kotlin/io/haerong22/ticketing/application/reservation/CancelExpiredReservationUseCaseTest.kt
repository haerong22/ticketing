package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.infrastructure.db.performance.SeatEntity
import io.haerong22.ticketing.infrastructure.db.performance.SeatJpaRepository
import io.haerong22.ticketing.infrastructure.db.reservation.ReservationEntity
import io.haerong22.ticketing.infrastructure.db.reservation.ReservationJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CancelExpiredReservationUseCaseTest(
    private val sut: CancelExpiredReservationUseCase,
    private val reservationJpaRepository: ReservationJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `만료된 예약 취소 및 좌석 상태 변경`() {
        // given
        seatJpaRepository.save(
            SeatEntity(1L, 10, 10000, SeatStatus.RESERVED)
        )
        reservationJpaRepository.save(
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            )
        )

        // when
        sut()

        // then
        val seat = seatJpaRepository.findById(1L).get()
        val reservation = reservationJpaRepository.findById(1L).get()

        assertThat(reservation.status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(seat.status).isEqualTo(SeatStatus.OPEN)
    }
}