package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import io.haerong22.ticketing.domain.user.UserException
import io.haerong22.ticketing.infrastructure.db.user.UserEntity
import io.haerong22.ticketing.infrastructure.db.user.UserJpaRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GetUserPointUseCaseTest(
    private val getUserPointUseCase: GetUserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `유저 포인트를 조회한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        val command = GetUserPointCommand(1L)

        // when
        val result = getUserPointUseCase.invoke(command)

        // then
        assertThat(result.userId).isEqualTo(1)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(0)
    }

    @Test
    fun `유저 포인트 조회 시 유저가 없으면 UserException 이 발생한다`() {
        // given
        val command = GetUserPointCommand(1L)

        // when, then
        assertThatThrownBy { getUserPointUseCase.invoke(command) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }
}