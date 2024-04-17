package io.haerong22.ticketing.application.queue.unit

import io.haerong22.ticketing.application.queue.ExitWaitingQueueUseCase
import io.haerong22.ticketing.domain.queue.QueueService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.*

@ExtendWith(MockitoExtension::class)
class ExitWaitingQueueUseCaseTest {

    @InjectMocks
    private lateinit var sut: ExitWaitingQueueUseCase

    @Mock
    private lateinit var queueService: QueueService

    @Test
    fun `대기열 퇴장`() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        sut(token)

        // then
        verify(queueService, times(1)).exit(token)
    }
}