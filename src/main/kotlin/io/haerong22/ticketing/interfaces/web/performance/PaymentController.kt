package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.interfaces.web.CommonResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments")
class PaymentController {

    @PostMapping
    fun payment(
        @RequestBody request: PerformanceRequest.Payment
    ): CommonResponse<Void> {
        return CommonResponse.ok()
    }
}