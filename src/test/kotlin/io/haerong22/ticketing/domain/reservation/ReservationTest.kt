package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReservationTest {

    @Test
    fun `userId, seatId로 예약 시 상태는 RESERVED, 만료시간은 5분 이다`() {
        // given
        val userId = 1L
        val seatId = 1L

        // when
        val result = Reservation.reserve(userId, seatId)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.seatId).isEqualTo(1L)
        assertThat(result.status).isEqualTo(ReservationStatus.RESERVED)
    }
}