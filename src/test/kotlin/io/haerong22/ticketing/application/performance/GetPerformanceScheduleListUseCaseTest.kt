package io.haerong22.ticketing.application.performance

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceEntity
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceJpaRepository
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceScheduleEntity
import io.haerong22.ticketing.infrastructure.db.performance.PerformanceScheduleJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class GetPerformanceScheduleListUseCaseTest(
    private val sut: GetPerformanceScheduleListUseCase,
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `공연 스케줄 정보를 조회한다`() {
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
        performanceScheduleJpaRepository.save(
            PerformanceScheduleEntity(
                1L,
                LocalDateTime.of(2024, 4, 12, 17, 0, 0),
                LocalDateTime.of(2024, 4, 12, 20, 0, 0),
                LocalDateTime.of(2024, 4, 1, 17, 0, 0),
            )
        )

        // when
        val result = sut(1L)

        // then
        assertThat(result.performanceId).isEqualTo(1L)
        assertThat(result.title).isEqualTo("공연1")
        assertThat(result.content).isEqualTo("내용1")
        assertThat(result.schedules).hasSize(2)
            .extracting("performanceScheduleId", "startAt", "endAt", "reservationAt")
            .containsExactlyInAnyOrder(
                tuple(
                    1L,
                    LocalDateTime.of(2024, 4, 11, 17, 0, 0),
                    LocalDateTime.of(2024, 4, 11, 20, 0, 0),
                    LocalDateTime.of(2024, 4, 1, 17, 0, 0),
                ),
                tuple(
                    2L,
                    LocalDateTime.of(2024, 4, 12, 17, 0, 0),
                    LocalDateTime.of(2024, 4, 12, 20, 0, 0),
                    LocalDateTime.of(2024, 4, 1, 17, 0, 0),
                ),
            )
    }
}
