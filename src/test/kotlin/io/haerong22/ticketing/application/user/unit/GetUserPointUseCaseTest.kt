package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import io.haerong22.ticketing.domain.user.UserReader
import io.haerong22.ticketing.stub.UserRepositoryStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetUserPointUseCaseTest {

    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        val userRepository = UserRepositoryStub()
        val userReader = UserReader(userRepository)
        val useCase = GetUserPointUseCase(userReader)

        val command = GetUserPointCommand(1L)

        // when
        val result = useCase(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저1")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}