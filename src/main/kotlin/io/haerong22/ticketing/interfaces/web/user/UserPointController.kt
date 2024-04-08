package io.haerong22.ticketing.interfaces.web.user

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.ChargeUserPointCommand
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import io.haerong22.ticketing.interfaces.web.CommonResponse
import io.haerong22.ticketing.interfaces.web.user.request.ChargeUserPointRequest
import io.haerong22.ticketing.interfaces.web.user.response.UserPointResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserPointController(
    private val getUserPointUseCase: GetUserPointUseCase,
    private val chargeUserPointUseCase: ChargeUserPointUseCase,
) {

    @GetMapping("/{user_id}/point")
    fun getUserPoint(
        @PathVariable(name = "user_id") userId: Long,
    ): CommonResponse<UserPointResponse> {
        val command = GetUserPointCommand(userId)
        val result = getUserPointUseCase(command)
        return CommonResponse.ok(UserPointResponse.toResponse(result))
    }

    @PatchMapping("/{user_id}/point")
    fun chargeUserPoint(
        @PathVariable(name = "user_id") userId: Long,
        @RequestBody request: ChargeUserPointRequest,
    ): CommonResponse<Unit> {
        val command = ChargeUserPointCommand(userId, request.amount)
        chargeUserPointUseCase(command)
        return CommonResponse.ok()
    }
}