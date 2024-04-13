package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Seat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SeatEntityTest {

    @Test
    fun `Seat 도메인으로 SeatEntity를 생성한다`() {
        // given
        val seat = Seat(
            seatId = 1L,
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.OPEN
        )

        // when
        val result = SeatEntity.of(seat)

        // then
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.performanceScheduleId).isEqualTo(1L)
        assertThat(result.seatNo).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(SeatStatus.OPEN)
    }
}