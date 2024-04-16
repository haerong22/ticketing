package io.haerong22.ticketing.application.reservation.command

import io.haerong22.ticketing.domain.common.BadRequestException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ReservationCommandTest {

    @Test
    fun `예약 command 생성 시 userId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = -1L
        val seatId = 1L

        // when, then
        assertThatThrownBy { ReservationCommand.Reserve(userId, seatId) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("userId는 양수 값 입니다.")
    }

    @Test
    fun `예약 command 생성 시 seatId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = 1L
        val seatId = -1L

        // when, then
        assertThatThrownBy { ReservationCommand.Reserve(userId, seatId) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("seatId는 양수 값 입니다.")
    }

    @Test
    fun `결제 command 생성 시 reservationId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val reservationId = -1L
        val userId = 1L

        // when, then
        assertThatThrownBy { ReservationCommand.Pay(userId, reservationId) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("reservationId는 양수 값 입니다.")
    }

    @Test
    fun `결제 command 생성 시 userId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val reservationId = 1L
        val userId = -1L

        // when, then
        assertThatThrownBy { ReservationCommand.Pay(userId, reservationId) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("userId는 양수 값 입니다.")
    }
}