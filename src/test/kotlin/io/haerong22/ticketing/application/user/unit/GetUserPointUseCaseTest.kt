package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import io.haerong22.ticketing.domain.user.UserService
import io.haerong22.ticketing.stub.UserReaderRepositoryStub
import io.haerong22.ticketing.stub.UserStoreRepositoryStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetUserPointUseCaseTest {

    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        val userService = UserService(UserReaderRepositoryStub(), UserStoreRepositoryStub())
        val useCase = GetUserPointUseCase(userService)

        val command = GetUserPointCommand(1L)

        // when
        val result = useCase(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저1")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}