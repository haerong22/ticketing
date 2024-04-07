package io.haerong22.ticketing.interfaces.web.queue

import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.interfaces.web.CommonResponse
import io.haerong22.ticketing.interfaces.web.queue.response.QueueResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/queue")
class QueueController {

    @PostMapping("/enter")
    fun enterWaitingQueue(): CommonResponse<QueueResponse> {
        return CommonResponse.ok(
            QueueResponse(UUID.randomUUID().toString(), 20, QueueStatus.WAITING)
        )
    }

    @GetMapping("/check")
    fun checkWaitingQueue(
        @RequestHeader("wq-token") token: String,
    ): CommonResponse<QueueResponse> {
        return CommonResponse.ok(
            QueueResponse(token, 10, QueueStatus.WAITING)
        )
    }

    @DeleteMapping("/exit")
    fun exitWaitingQueue(
        @RequestHeader("wq-token") token: String,
    ): CommonResponse<Void> {
        return CommonResponse.ok()
    }
}