package io.haerong22.ticketing.application.user.command

import io.haerong22.ticketing.domain.common.BadRequestException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ChargeUserPointCommandTest {

    @Test
    fun `userId는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = -1L
        val amount = 0

        // when, then
        assertThatThrownBy { ChargeUserPointCommand(userId, amount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("userId는 양수 값 입니다.")
    }

    @Test
    fun `amount는 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given
        val userId = 1L
        val amount = -1

        // when, then
        assertThatThrownBy { ChargeUserPointCommand(userId, amount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("amount는 양수 값 입니다.")
    }
}