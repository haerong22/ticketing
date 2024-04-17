package io.haerong22.ticketing.interfaces.web.queue

import io.haerong22.ticketing.application.queue.EnterWaitingQueueUseCase
import io.haerong22.ticketing.application.queue.ExitWaitingQueueUseCase
import io.haerong22.ticketing.application.queue.GetWaitingQueueStatusUseCase
import io.haerong22.ticketing.interfaces.web.CommonResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/queue")
class QueueController(
    private val enterWaitingQueueUseCase: EnterWaitingQueueUseCase,
    private val getWaitingQueueStatusUseCase: GetWaitingQueueStatusUseCase,
    private val exitWaitingQueueUseCase: ExitWaitingQueueUseCase,
) {

    @PostMapping("/enter")
    fun enterWaitingQueue(): CommonResponse<QueueResponse.Info> {
        val result = enterWaitingQueueUseCase()
        return CommonResponse.ok(
            QueueResponse.Info.toResponse(result)
        )
    }

    @GetMapping("/status")
    fun checkWaitingQueue(
        @RequestHeader("wq-token") token: String,
    ): CommonResponse<QueueResponse.Info> {
        val result = getWaitingQueueStatusUseCase(token)
        return CommonResponse.ok(
            QueueResponse.Info.toResponse(result)
        )
    }

    @DeleteMapping("/exit")
    fun exitWaitingQueue(
        @RequestHeader("wq-token") token: String,
    ): CommonResponse<Void> {
        exitWaitingQueueUseCase.invoke(token)
        return CommonResponse.ok()
    }
}