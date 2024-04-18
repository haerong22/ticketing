package io.haerong22.ticketing.interfaces.controller.user.response

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.interfaces.controller.user.UserResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserPointResponseTest {

    @Test
    fun `User 로 UserPointResponse 를 생성 할 수 있다`() {
        // given
        val user = User(1L, "유저", UserPoint(0))

        // when
        val result = UserResponse.Point.toResponse(user)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.userName).isEqualTo("유저")
        assertThat(result.point).isEqualTo(0)
    }
}