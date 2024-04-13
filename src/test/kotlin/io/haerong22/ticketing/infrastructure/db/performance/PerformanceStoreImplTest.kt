package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.Seat
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import

@Import(PerformanceStoreImpl::class)
class PerformanceStoreImplTest(
    private val sut: PerformanceStoreImpl,
    private val seatJpaRepository: SeatJpaRepository,
) : DbTestSupport() {

    @Test
    fun `Seat 도메인을 영속화 한다`() {
        // given
        val seat = Seat(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            status = SeatStatus.OPEN
        )

        // when
        sut.save(seat)

        // then
        val result = seatJpaRepository.findById(1L).get()

        assertThat(result.id).isEqualTo(1L)
        assertThat(result.performanceScheduleId).isEqualTo(1L)
        assertThat(result.seatNo).isEqualTo(1)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(SeatStatus.OPEN)
    }

}