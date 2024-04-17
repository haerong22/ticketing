package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.domain.queue.QueueService
import org.springframework.stereotype.Service

@Service
class ExitWaitingQueueUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke(token: String) {
        queueService.exit(token)
    }
}