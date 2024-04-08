package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.command.ChargeUserPointCommand
import io.haerong22.ticketing.domain.user.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
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
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var userModifier: UserModifier

    @Mock
    private lateinit var userPointHistoryAppender: UserPointHistoryAppender

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        val user = User(1L, "유저", UserPoint(0))
        val expected = User(1L, "유저", UserPoint(10000))

        val command = ChargeUserPointCommand(1L, 10000)

        given(userReader.getUserByIdWithLock(1L)).willReturn(user)
        given(userModifier.updateUserPoint(any(), any())).willReturn(expected)

        // when
        val result = sut(command)

        // then
        verify(userReader, times(1)).getUserByIdWithLock(any())
        verify(userModifier, times(1)).updateUserPoint(any(), any())
        verify(userPointHistoryAppender, times(1)).append(any(), any())

        assertThat(result).isEqualTo(expected)
    }
}