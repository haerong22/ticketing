package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SeatJpaRepositoryTest(
    private val seatJpaRepository: SeatJpaRepository,
    private val em: EntityManager
) : DbTestSupport() {

    @Test
    fun `좌석 조회 시 락을 획득한다`() {
        // given
        seatJpaRepository.save(SeatEntity(1L, 1, 10000, SeatStatus.RESERVED))
        val seatId = 1L

        // when
        val result = seatJpaRepository.findByIdForUpdate(seatId)

        // then
        assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }

    @Test
    fun `seatId 리스트로 좌석 상태를 변경한다`() {
        // given
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 10, price = 10000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 11, price = 12000, SeatStatus.RESERVED))
        seatJpaRepository.save(SeatEntity(performanceScheduleId = 1L, seatNo = 12, price = 15000, SeatStatus.RESERVED))

        val seatIds = listOf(1L, 2L)
        val status = SeatStatus.OPEN

        // when
        seatJpaRepository.updateStatus(seatIds, status)

        // then
        val result = seatJpaRepository.findAll()

        assertThat(result[0].status).isEqualTo(SeatStatus.OPEN)
        assertThat(result[1].status).isEqualTo(SeatStatus.OPEN)
        assertThat(result[2].status).isEqualTo(SeatStatus.RESERVED)
    }
}
