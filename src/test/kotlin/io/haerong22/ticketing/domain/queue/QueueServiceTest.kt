package io.haerong22.ticketing.domain.queue

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class QueueServiceTest {

    @InjectMocks
    private lateinit var sut: QueueService

    @Mock
    private lateinit var queueReader: QueueReader

    @Mock
    private lateinit var queueStore: QueueStore

    @Mock
    private lateinit var tokenGenerator: TokenGenerator

    @Test
    fun `대기 상태의 토큰을 진행 상태로 변경한다`() {
        // given
        val maxUserCount = 50
        val activeUserCount = 48
        val targetCount = maxUserCount - activeUserCount
        val targetIds = listOf(49L, 50L)

        given { queueReader.getActiveCount() }.willAnswer { activeUserCount }
        given { queueReader.getTargetTokenIds(targetCount) }.willAnswer { targetIds }

        // when
        sut.activeTokens(maxUserCount)

        // then
        verify(queueReader, times(1)).getActiveCount()
        verify(queueReader, times(1)).getTargetTokenIds(2)
        verify(queueStore, times(1)).activeTokens(listOf(49L, 50L))
    }
}