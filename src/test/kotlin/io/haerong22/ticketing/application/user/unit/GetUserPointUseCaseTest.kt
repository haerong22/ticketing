package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.stub.UserReaderStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetUserPointUseCaseTest {

    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        val useCase = GetUserPointUseCase(UserReaderStub())

        val command = UserCommand.GetPoint(1L)

        // when
        val result = useCase(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저1")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}