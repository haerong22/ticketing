package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.PaymentStatus
import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.Payment
import io.haerong22.ticketing.domain.reservation.Reservation
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(ReservationStoreImpl::class)
class ReservationStoreImplTest(
    private val sut: ReservationStoreImpl,
    private val reservationJpaRepository: ReservationJpaRepository,
    private val paymentJpaRepository: PaymentJpaRepository,
) : DbTestSupport() {

    @Test
    fun `Reservation 도메인을 영속화 한다`() {
        // given
        val expiredAt = LocalDateTime.now().plusMinutes(5)
        val reservation = Reservation(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expiredAt,
        )

        // when
        sut.save(reservation)

        // then
        val result = reservationJpaRepository.findById(1L).get()

        assertThat(result.id).isEqualTo(1L)
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.seatId).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(ReservationStatus.RESERVED)
        assertThat(result.expiredAt).isEqualTo(expiredAt)
    }

    @Test
    fun `Payment 도메인을 영속화 한다`() {
        // given
        val payment = Payment(
            reservationId = 1L,
            price = 10000,
            status = PaymentStatus.COMPLETE,
        )

        // when
        sut.save(payment)

        // then
        val result = paymentJpaRepository.findById(1L).get()

        assertThat(result.id).isEqualTo(1L)
        assertThat(result.reservationId).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(PaymentStatus.COMPLETE)
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

        // when
        sut.cancelExpiredReservation(ids)

        // then
        val result = reservationJpaRepository.findAll()
        assertThat(result[0].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[1].status).isEqualTo(ReservationStatus.EXPIRED)
        assertThat(result[2].status).isEqualTo(ReservationStatus.RESERVED)
    }
}