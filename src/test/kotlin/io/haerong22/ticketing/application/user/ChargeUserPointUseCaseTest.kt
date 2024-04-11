package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.IntegrationTestSupport
import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.user.UserException
import io.haerong22.ticketing.infrastructure.db.user.PointHistoryJpaRepository
import io.haerong22.ticketing.infrastructure.db.user.UserEntity
import io.haerong22.ticketing.infrastructure.db.user.UserJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture.allOf
import java.util.concurrent.CompletableFuture.runAsync

class ChargeUserPointUseCaseTest(
    private val sut: ChargeUserPointUseCase,
    private val userJpaRepository: UserJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
) : IntegrationTestSupport() {

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        val command = UserCommand.ChargePoint(1L, 1000)

        // when
        val result = sut(command)

        // then
        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(1000)
    }

    @Test
    fun `유저 포인트 충전 시 유저가 없으면 UserException이 발생한다`() {
        // given
        val command = UserCommand.ChargePoint(1L, 1000)

        // when, then
        assertThatThrownBy { sut(command) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }

    @Test
    fun `유저 포인트 충전 동시성 테스트`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))

        // when
        allOf(
            runAsync { sut(UserCommand.ChargePoint(1L, 1000)) },
            runAsync { sut(UserCommand.ChargePoint(1L, 2000)) },
            runAsync { sut(UserCommand.ChargePoint(1L, 3000)) },
            runAsync { sut(UserCommand.ChargePoint(1L, 4000)) },
            runAsync { sut(UserCommand.ChargePoint(1L, 5000)) },
        ).join()

        // then
        val result = userJpaRepository.findById(1L).get()
        val count = pointHistoryJpaRepository.count()

        assertThat(result.point).isEqualTo(1000 + 2000 + 3000 + 4000 + 5000)
        assertThat(count).isEqualTo(5)
    }
}