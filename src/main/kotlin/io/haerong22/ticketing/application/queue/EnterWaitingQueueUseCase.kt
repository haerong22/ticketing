package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.domain.queue.QueueService
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Service

@Service
class EnterWaitingQueueUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke() : WaitingQueue {
        return queueService.enter()
    }
}