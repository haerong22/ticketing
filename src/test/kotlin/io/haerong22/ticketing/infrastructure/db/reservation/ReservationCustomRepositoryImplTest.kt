package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.infrastructure.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(ReservationCustomRepositoryImpl::class)
class ReservationCustomRepositoryImplTest(
    private val sut: ReservationCustomRepositoryImpl,
    private val reservationJpaRepository: ReservationJpaRepository,
    private val em: EntityManager,
) : DbTestSupport() {

    @Test
    fun `예약 조회 시 락을 획득한다`() {
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
        val result = sut.findByIdForUpdate(reservationId)

        // then
        assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }

    @Test
    fun `reservationId 리스트로 상태를 변경한다`() {
        // given
        reservationJpaRepository.saveAll(
            listOf(
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
            )
        )

        val ids = listOf(1L, 2L)
        val status = ReservationStatus.EXPIRED

        // when
        sut.updateStatus(ids, status)

        // then
        val result = reservationJpaRepository.findAll()
        assertThat(result[0].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[1].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[2].status).isEqualTo(ReservationStatus.RESERVED)
    }
}
