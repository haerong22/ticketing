package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.domain.user.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times

@ExtendWith(MockitoExtension::class)
class ChargeUserPointUseCaseTest {

    @InjectMocks
    private lateinit var sut: ChargeUserPointUseCase

    @Mock
    private lateinit var userService: UserService

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        val findUser = User(1L, "유저", UserPoint(0))
        val afterCharge = User(1L, "유저", UserPoint(10000))

        val command = UserCommand.ChargePoint(1L, 10000)

        given(userService.getUserWithLock(1L)).willReturn(findUser)
        given(userService.chargePoint(findUser, 10000)).willReturn(afterCharge)

        // when
        val result = sut(command)

        // then
        verify(userService, times(1)).getUserWithLock(1L)
        verify(userService, times(1)).chargePoint(findUser, 10000)

        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}
