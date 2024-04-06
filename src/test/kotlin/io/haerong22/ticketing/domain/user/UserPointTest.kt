package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.BadRequestException
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

}