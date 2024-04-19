package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.Pageable
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(PerformanceReaderImpl::class)
class PerformanceReaderImplTest(
    private val sut: PerformanceReaderImpl,
    private val performanceJpaRepository: PerformanceJpaRepository,
    private val performanceScheduleJpaRepository: PerformanceScheduleJpaRepository,
    private val seatJpaRepository: SeatJpaRepository,
) : DbTestSupport() {

    @Test
    fun `공연 리스트를 조회 할 수 있다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceJpaRepository.save(PerformanceEntity("공연2", "내용2"))
        performanceJpaRepository.save(PerformanceEntity("공연3", "내용3"))

        val pageable = Pageable(1, 1)

        // when
        val result = sut.getPerformanceList(pageable)

        // then
        assertThat(result.list).hasSize(1)
            .extracting("title", "content")
            .containsExactlyInAnyOrder(
                tuple("공연1", "내용1")
            )

        assertThat(result.pageInfo.pageNo).isEqualTo(1)
        assertThat(result.pageInfo.pageSize).isEqualTo(1)
        assertThat(result.pageInfo.totalElements).isEqualTo(3)
    }

    @Test
    fun `공연 정보가 없으면 빈 리스트를 응답한다`() {
        // given
        performanceJpaRepository.save(PerformanceEntity("공연1", "내용1"))
        performanceJpaRepository.save(PerformanceEntity("공연2", "내용2"))
        performanceJpaRepository.save(PerformanceEntity("공연3", "내용3"))

        val pageable = Pageable(2, 5)

        // when
        val result = sut.getPerformanceList(pageable)

        // then
        assertThat(result.list).isEmpty()

        assertThat(result.pageInfo.pageNo).isEqualTo(2)
        assertThat(result.pageInfo.pageSize).isEqualTo(5)
        assertThat(result.pageInfo.totalElements).isEqualTo(3)
    }

    @Test
    fun `공연 정보를 조회 할 수 있다`() {
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
        val result = sut.getPerformance(1L)!!

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

    @Test
    fun `예약 가능한 좌석 리스트를 조회 할 수 있다`() {
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
        val result = sut.getAvailableSeatList(performanceScheduleId)

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].seatId).isEqualTo(2)
        assertThat(result[0].seatNo).isEqualTo(2)
        assertThat(result[0].price).isEqualTo(20000)
    }
}