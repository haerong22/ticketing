package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.BadRequestException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class UserPointTest {

    @Test
    fun `포인트를 음수로 초기화 하면 BadRequestException 이 발생한다`() {
        // given

        // when, then
        assertThatThrownBy { UserPoint(-1) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("포인트는 음수로 초기화 할 수 없습니다.")
    }

    @Test
    fun `포인트를 충전한다`() {
        // given
        val userPoint = UserPoint(0)
        val chargeAmount = 10000

        // when
        val result = userPoint.charge(chargeAmount)

        // then
        assertThat(result.amount).isEqualTo(10000)
    }

    @Test
    fun `포인트 충전 시 음수값이면 BadRequestException 이 발생한다`() {
        // given
        val userPoint = UserPoint(0)
        val chargeAmount = -1

        // when, then
        assertThatThrownBy { userPoint.charge(chargeAmount) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("충전 포인트는 0 이상 입니다.")
    }

}