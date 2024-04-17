package io.haerong22.ticketing.application.queue.unit

import io.haerong22.ticketing.application.queue.EnterWaitingQueueUseCase
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueService
import io.haerong22.ticketing.domain.queue.TokenGenerator
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class EnterWaitingQueueUseCaseTest {

    @InjectMocks
    private lateinit var sut: EnterWaitingQueueUseCase

    @Mock
    private lateinit var queueService: QueueService

    @Test
    fun `대기열 입장`() {
        // given
        val token = TokenGenerator().generate()
        val queue = WaitingQueue(
            queueId = 1L,
            token = token,
            rank = 10,
            status = QueueStatus.WAITING
        )

        given { queueService.enter() }.willAnswer { queue }

        // when
        val result = sut()

        // then
        verify(queueService, times(1)).enter()

        assertThat(result.queueId).isEqualTo(1L)
        assertThat(result.token).isEqualTo(token)
        assertThat(result.rank).isEqualTo(10)
        assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        assertThat(result.expiredAt).isNull()
    }

}