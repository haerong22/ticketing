package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationJpaRepositoryTest(
    private val reservationJpaRepository: ReservationJpaRepository,
    private val em: EntityManager
) : DbTestSupport() {

    @Test
    fun `좌석 조회 시 락을 획득한다`() {
        // given
        reservationJpaRepository.save(
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now(),
            )
        )
        val reservationId = 1L

        // when
        val result = reservationJpaRepository.findByIdForUpdate(reservationId).get()

        // then
        assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }
}