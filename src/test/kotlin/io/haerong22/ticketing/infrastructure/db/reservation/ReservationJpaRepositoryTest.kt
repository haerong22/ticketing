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

    @Test
    fun `만료된 예약 리스트를 조회한다`() {
        // given
        reservationJpaRepository.saveAll(listOf(
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            ),
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            ),
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().plusMinutes(5),
            )
        ))

        val status = ReservationStatus.RESERVED
        val date = LocalDateTime.now()

        // when
        val result = reservationJpaRepository.findAllByStatusAndExpiredAtBefore(status, date)

        // then
        assertThat(result).hasSize(2)
        assertThat(result[0].id).isEqualTo(1L)
        assertThat(result[1].id).isEqualTo(2L)
    }

    @Test
    fun `reservationId 리스트로 상태를 변경한다`() {
        // given
        reservationJpaRepository.saveAll(listOf(
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            ),
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().minusMinutes(5),
            ),
            ReservationEntity(
                userId = 1L,
                seatId = 1L,
                price = 10000,
                status = ReservationStatus.RESERVED,
                expiredAt = LocalDateTime.now().plusMinutes(5),
            )
        ))

        val ids = listOf(1L, 2L)
        val status = ReservationStatus.EXPIRED

        // when
        reservationJpaRepository.updateStatus(ids, status)

        // then
        val result = reservationJpaRepository.findAll()
        assertThat(result[0].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[1].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[2].status).isEqualTo(ReservationStatus.RESERVED)
    }
}