package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.command.ChargeUserPointCommand
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
import org.mockito.kotlin.any
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
        val user = User(1L, "유저", UserPoint(0))
        val expected = User(1L, "유저", UserPoint(10000))

        val command = ChargeUserPointCommand(1L, 10000)

        given(userService.chargePoint(command.userId, command.amount)).willReturn(expected)

        // when
        val result = sut(command)

        // then
        verify(userService, times(1)).chargePoint(any(), any())

        assertThat(result).isEqualTo(expected)
    }
}