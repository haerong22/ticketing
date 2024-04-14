package io.haerong22.ticketing.infrastructure.db.performance

import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions
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
        val result = seatJpaRepository.findByIdForUpdate(seatId).get()

        // then
        Assertions.assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }
}