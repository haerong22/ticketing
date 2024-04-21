package io.haerong22.ticketing.application.reservation

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.application.reservation.command.ReservationCommand
import io.haerong22.ticketing.domain.common.enums.ReservationStatus
import io.haerong22.ticketing.domain.common.enums.SeatStatus
import io.haerong22.ticketing.domain.performance.PerformanceException
import io.haerong22.ticketing.infrastructure.db.performance.SeatEntity
import io.haerong22.ticketing.infrastructure.db.performance.SeatJpaRepository
import io.haerong22.ticketing.infrastructure.db.reservation.ReservationJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync

class PerformanceSeatReservationUseCaseTest(
    private val sut: PerformanceSeatReservationUseCase,
    private val seatJpaRepository: SeatJpaRepository,
    private val reservationJpaRepository: ReservationJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `좌석 예약 동시성 테스트`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.OPEN
        )
        seatJpaRepository.save(seatEntity)

        // when
        val futures = arrayOf(
            runAsync { sut(ReservationCommand.Reserve(userId = 1L, seatId = 1L)) },
            runAsync { sut(ReservationCommand.Reserve(userId = 2L, seatId = 1L)) },
            runAsync { sut(ReservationCommand.Reserve(userId = 3L, seatId = 1L)) },
        )

        allOf(*futures)
            .exceptionally { null }
            .join()

        // then
        val errorCount = futures.count { it.isCompletedExceptionally }
        assertThat(errorCount).isEqualTo(2)

        val reservationCount = reservationJpaRepository.count()
        assertThat(reservationCount).isEqualTo(1)
    }

    @Test
    fun `좌석을 예약할 수 있다`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.OPEN
        )
        seatJpaRepository.save(seatEntity)

        val command = ReservationCommand.Reserve(userId = 1L, seatId = 1L)

        // when
        sut(command)

        // then
        val seat = seatJpaRepository.findById(1L).get()
        assertThat(seat.id).isEqualTo(1L)
        assertThat(seat.performanceScheduleId).isEqualTo(1L)
        assertThat(seat.seatNo).isEqualTo(1)
        assertThat(seat.price).isEqualTo(10000)
        assertThat(seat.status).isEqualTo(SeatStatus.RESERVED)

        val reservation = reservationJpaRepository.findById(1L).get()
        assertThat(reservation.id).isEqualTo(1L)
        assertThat(reservation.seatId).isEqualTo(1L)
        assertThat(reservation.userId).isEqualTo(1L)
        assertThat(reservation.status).isEqualTo(ReservationStatus.RESERVED)
        assertThat(reservation.expiredAt).isNotNull()
    }

    @Test
    fun `좌석 상태가 RESERVED 이면 PerformanceException 이 발생한다`() {
        // given
        val seatEntity = SeatEntity(
            performanceScheduleId = 1L,
            seatNo = 1,
            price = 10000,
            SeatStatus.RESERVED
        )
        seatJpaRepository.save(seatEntity)

        val command = ReservationCommand.Reserve(userId = 1L, seatId = 1L)

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(PerformanceException::class.java)
            .hasMessage("이미 예약된 좌석입니다.")
    }
}
