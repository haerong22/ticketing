package io.haerong22.ticketing.application.queue.unit

import io.haerong22.ticketing.application.queue.GetWaitingQueueStatusUseCase
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.QueueService
import io.haerong22.ticketing.domain.queue.TokenGenerator
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class GetWaitingQueueStatusUseCaseTest {

    @InjectMocks
    private lateinit var sut: GetWaitingQueueStatusUseCase

    @Mock
    private lateinit var queueService: QueueService

    @Test
    fun `대기열 상태 화인`() {
        // given
        val token = TokenGenerator().generate()
        val queue = WaitingQueue(
            queueId = 1L,
            token = token,
            rank = 10,
            status = QueueStatus.WAITING
        )

        given { queueService.getMyQueueStatus(token) }.willAnswer { queue }

        // when
        val result = sut(token)

        // then
        verify(queueService, times(1)).getMyQueueStatus(token)

        Assertions.assertThat(result.queueId).isEqualTo(1L)
        Assertions.assertThat(result.token).isEqualTo(token)
        Assertions.assertThat(result.rank).isEqualTo(10)
        Assertions.assertThat(result.status).isEqualTo(QueueStatus.WAITING)
        Assertions.assertThat(result.expiredAt).isNull()
    }
}