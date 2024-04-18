package io.haerong22.ticketing.application.queue

import io.haerong22.ticketing.domain.queue.QueueService
import org.springframework.stereotype.Service

@Service
class ActiveTokenUseCase(
    private val queueService: QueueService,
) {

    operator fun invoke() {
        val maxUserCount = 50
        queueService.clearExpiredToken()
        queueService.activeTokens(maxUserCount)
    }
}