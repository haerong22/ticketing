package io.haerong22.ticketing.domain.queue

interface QueueStore {

    fun enter(waitingQueue: WaitingQueue): WaitingQueue

    fun exit(token: String)

    fun clearExpiredToken()

    fun activeTokens(targets: List<Long>)
}
