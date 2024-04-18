package io.haerong22.ticketing.application.queue.unit

import io.haerong22.ticketing.application.queue.ActiveTokenUseCase
import io.haerong22.ticketing.domain.queue.QueueService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class ActiveTokenUseCaseTest {

    @InjectMocks
    private lateinit var sut: ActiveTokenUseCase

    @Mock
    private lateinit var queueService: QueueService

    @Test
    fun `대기 상태인 토큰들을 진행 상태로 변경한다`() {
        // given
        val maxUserCount = 50

        // when
        sut(maxUserCount)

        // then
        verify(queueService, times(1)).clearExpiredToken()
        verify(queueService, times(1)).activeTokens(maxUserCount)
    }
}