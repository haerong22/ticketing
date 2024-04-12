package io.haerong22.ticketing.application.user.unit

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.common.enums.TransactionType
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserReader
import io.haerong22.ticketing.domain.user.UserStore
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
    private lateinit var userReader: UserReader

    @Mock
    private lateinit var userStore: UserStore

    @Test
    fun `유저 포인트를 충전한다`() {
        // given
        val findUser = User(1L, "유저", UserPoint(0))
        val afterCharge = User(1L, "유저", UserPoint(10000))
        val userHistory = UserPointHistory(10000, TransactionType.CHARGE)

        val command = UserCommand.ChargePoint(1L, 10000)

        given(userReader.getUserWithPessimisticLock(1L)).willReturn(findUser)

        // when
        val result = sut(command)

        // then
        verify(userReader, times(1)).getUserWithPessimisticLock(1L)
        verify(userStore, times(1)).saveUser(afterCharge)
        verify(userStore, times(1)).savePointHistory(afterCharge, userHistory)

        assertThat(result.userId).isEqualTo(1L)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(10000)
    }
}