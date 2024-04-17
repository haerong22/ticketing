package io.haerong22.ticketing.domain.queue

interface QueueStore {

    fun enter(waitingQueue: WaitingQueue): WaitingQueue
}