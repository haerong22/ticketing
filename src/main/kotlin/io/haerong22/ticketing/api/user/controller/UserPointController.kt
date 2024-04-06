package io.haerong22.ticketing.api.user.controller

import io.haerong22.ticketing.api.CommonResponse
import io.haerong22.ticketing.api.user.controller.response.UserPointResponse
import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserPointController(
    private val getUserPointUseCase: GetUserPointUseCase
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
    ): CommonResponse<UserPointResponse> {
        return CommonResponse.ok()
    }
}