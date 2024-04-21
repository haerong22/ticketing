package io.haerong22.ticketing.interfaces.controller.user

import io.haerong22.ticketing.application.user.ChargeUserPointUseCase
import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.interfaces.controller.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserPointController(
    private val getUserPointUseCase: GetUserPointUseCase,
    private val chargeUserPointUseCase: ChargeUserPointUseCase,
) {

    @GetMapping("/{user_id}/point")
    fun getUserPoint(
        @PathVariable(name = "user_id") userId: Long,
    ): CommonResponse<UserResponse.Point> {
        val command = UserCommand.GetPoint(userId)
        val result = getUserPointUseCase(command)
        return CommonResponse.ok(UserResponse.Point.toResponse(result))
    }

    @PatchMapping("/{user_id}/point")
    fun chargeUserPoint(
        @PathVariable(name = "user_id") userId: Long,
        @RequestBody request: UserRequest.ChargePoint,
    ): CommonResponse<Unit> {
        val command = UserCommand.ChargePoint(userId, request.amount)
        chargeUserPointUseCase(command)
        return CommonResponse.ok()
    }
}
