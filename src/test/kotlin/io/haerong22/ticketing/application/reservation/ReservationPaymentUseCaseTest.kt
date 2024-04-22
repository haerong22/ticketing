package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.common.enums.PaymentStatus
import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.reservation.ReservationException
import io.haerong22.ticketing.domain.user.UserException
import io.haerong22.ticketing.infrastructure.db.reservation.PaymentJpaRepository
import io.haerong22.ticketing.infrastructure.db.reservation.ReservationEntity
import io.haerong22.ticketing.infrastructure.db.reservation.ReservationJpaRepository
import io.haerong22.ticketing.infrastructure.db.user.UserEntity
import io.haerong22.ticketing.infrastructure.db.user.UserJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync

class ReservationPaymentUseCaseTest(
    private val sut: ReservationPaymentUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val reservationJpaRepository: ReservationJpaRepository,
    private val paymentJpaRepository: PaymentJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `예약 좌석 결제 동시성 테스트`() {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 10000,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().plusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        // when
        val futures = arrayOf(
            runAsync { sut(ReservationCommand.Pay(userId = 1L, reservationId = 1L)) },
            runAsync { sut(ReservationCommand.Pay(userId = 1L, reservationId = 1L)) },
            runAsync { sut(ReservationCommand.Pay(userId = 1L, reservationId = 1L)) },
        )

        allOf(*futures)
            .exceptionally { null }
            .join()

        // then
        val errorCount = futures.count { it.isCompletedExceptionally }
        assertThat(errorCount).isEqualTo(2)
    }

    @Test
    fun `예약한 좌석을 결제한다`() {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 10000,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().plusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        val command = ReservationCommand.Pay(
            userId = 1L,
            reservationId = 1L,
        )

        // when
        sut(command)

        // then
        val user = userJpaRepository.findById(1L).get()
        assertThat(user.id).isEqualTo(1L)
        assertThat(user.name).isEqualTo("유저")
        assertThat(user.point).isEqualTo(0)

        val reservation = reservationJpaRepository.findById(1L).get()
        assertThat(reservation.id).isEqualTo(1L)
        assertThat(reservation.userId).isEqualTo(1L)
        assertThat(reservation.seatId).isEqualTo(1L)
        assertThat(reservation.price).isEqualTo(10000)
        assertThat(reservation.status).isEqualTo(ReservationStatus.COMPLETE)
        assertThat(reservation.expiredAt).isNotNull()

        val payment = paymentJpaRepository.findById(1L).get()
        assertThat(payment.id).isEqualTo(1L)
        assertThat(payment.reservationId).isEqualTo(1L)
        assertThat(payment.price).isEqualTo(10000)
        assertThat(payment.status).isEqualTo(PaymentStatus.COMPLETE)
    }

    @ParameterizedTest
    @ValueSource(strings = ["COMPLETE", "CANCEL", "EXPIRED"])
    fun `예약한 좌석을 결제 시 예약 상태가 아니면 ReservationException 이 발생한다`(status: ReservationStatus) {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 10000,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().plusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = status,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        val command = ReservationCommand.Pay(
            userId = 1L,
            reservationId = 1L,
        )

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약상태가 아닙니다.")
    }

    @Test
    fun `예약한 좌석을 결제 시 예약한 유저가 아니면 ReservationException 이 발생한다`() {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 10000,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().plusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 2L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        val command = ReservationCommand.Pay(
            userId = 1L,
            reservationId = 1L,
        )

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약한 유저가 아닙니다.")
    }

    @Test
    fun `예약한 좌석을 결제 시 예약 가능 시간이 만료 되었으면 ReservationException 이 발생한다`() {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 10000,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().minusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        val command = ReservationCommand.Pay(
            userId = 1L,
            reservationId = 1L,
        )

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(ReservationException::class.java)
            .hasMessage("예약이 만료되었습니다.")
    }

    @Test
    fun `예약한 좌석을 결제 시 잔액이 부족하면 UserException 이 발생한다`() {
        // given
        val userEntity = UserEntity(
            name = "유저",
            point = 0,
        )
        userJpaRepository.save(userEntity)

        val expired = LocalDateTime.now().plusMinutes(5)
        val reservationEntity = ReservationEntity(
            userId = 1L,
            seatId = 1L,
            price = 10000,
            status = ReservationStatus.RESERVED,
            expiredAt = expired,
        )
        reservationJpaRepository.save(reservationEntity)

        val command = ReservationCommand.Pay(
            userId = 1L,
            reservationId = 1L,
        )

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("잔액이 부족합니다.")
    }
}
