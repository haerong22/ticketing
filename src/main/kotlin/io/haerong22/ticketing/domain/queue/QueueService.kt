package io.haerong22.ticketing.domain.queue

import org.springframework.stereotype.Service

@Service
class QueueService(
    private val queueReader: QueueReader,
    private val queueStore: QueueStore,
    private val tokenGenerator: TokenGenerator,
) {

    fun enter() : WaitingQueue {
        val token = tokenGenerator.generate()
        val waitingQueue = WaitingQueue.enter(token)
        return queueStore.enter(waitingQueue)
    }

    fun getMyQueueStatus(token: String) : WaitingQueue {
        return queueReader.getQueueStatus(token)
    }
}