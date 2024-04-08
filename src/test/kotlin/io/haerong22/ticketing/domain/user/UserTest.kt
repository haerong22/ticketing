package io.haerong22.ticketing.domain.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun `유저 포인트 충전 시 기존 수량에 요청 수량이 더한 값을 응답한다`() {
        // given
        val user = User(1L, "유저", UserPoint(0))
        val chargeAmount = 10000

        // when
        val result = user.chargeUserPoint(chargeAmount)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}