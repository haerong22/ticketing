package io.haerong22.ticketing.domain.queue

interface QueueReader {

    fun getQueueStatus(token: String): WaitingQueue?

    fun getActiveCount(): Int

    fun getTargetTokenIds(targetCount: Int): List<Long>
}
