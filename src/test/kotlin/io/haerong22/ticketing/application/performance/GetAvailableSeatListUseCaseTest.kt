package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceEntity
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceJpaRepository
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceScheduleEntity
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceScheduleJpaRepository
import io.haerong22.ticketing.infrastructure.db.performance.SeatEntity
import io.haerong22.ticketing.infrastructure.db.performance.SeatJpaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetAvailableSeatListUseCaseTest(
    private val sut: GetAvailableSeatListUseCase,
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `예약 가능한 좌석 리스트를 조회한다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceScheduleJpaRepository.save(
            PerformanceScheduleEntity(
                1L,
                LocalDateTime.of(2024, 4, 11, 17, 0, 0),
                LocalDateTime.of(2024, 4, 11, 20, 0, 0),
                LocalDateTime.of(2024, 4, 1, 17, 0, 0),
            )
        )
        seatJpaRepository.save(SeatEntity(1L, 1, 10000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(1L, 2, 20000, SeatStatus.OPEN))

        val performanceScheduleId = 1L

        // when
        val result = sut(performanceScheduleId)

        // then
        Assertions.assertThat(result).hasSize(1)
        Assertions.assertThat(result[0].seatId).isEqualTo(2)
        Assertions.assertThat(result[0].seatNo).isEqualTo(2)
        Assertions.assertThat(result[0].price).isEqualTo(20000)
    }
}