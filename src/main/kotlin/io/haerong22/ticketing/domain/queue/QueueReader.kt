package io.haerong22.ticketing.domain.queue

interface QueueReader {

    fun getQueueStatus(token: String): WaitingQueue
}