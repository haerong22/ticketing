package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Reservation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationEntityTest {

    @Test
    fun `Reservation 도메인으로 ReservationEntity를 생성한다`() {
        // given
        val expiredAt = LocalDateTime.now()
        val reservation = Reservation(
            reservationId = 1L,
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expiredAt,
        )

        // when
        val result = ReservationEntity.of(reservation)

        // then
        Assertions.assertThat(result.id).isEqualTo(1L)
        Assertions.assertThat(result.userId).isEqualTo(1L)
        Assertions.assertThat(result.seatId).isEqualTo(1L)
        Assertions.assertThat(result.price).isEqualTo(10000)
        Assertions.assertThat(result.status).isEqualTo(ReservationStatus.RESERVED)
        Assertions.assertThat(result.expiredAt).isEqualTo(expiredAt)
    }
}