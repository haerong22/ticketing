package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.domain.queue.QueueService
import io.haerong22.ticketing.domain.queue.WaitingQueue
import org.springframework.stereotype.Service

@Service
class GetWaitingQueueStatusUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String) : WaitingQueue {
        return queueService.getMyQueueStatus(token)
    }
}