package io.haerong22.ticketing.api.user.controller

import io.haerong22.ticketing.api.CommonResponse
import io.haerong22.ticketing.api.user.controller.response.UserPointResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserPointController {

    @GetMapping("/{user_id}/point")
    fun getUserPoint(
        @PathVariable(name = "user_id") userId: Long,
    ): CommonResponse<UserPointResponse> {

        return CommonResponse.ok(
            UserPointResponse(userId, "유저%d".format(userId),10000)
        )
    }

    @PatchMapping("/{user_id}/point")
    fun chargeUserPoint(
        @PathVariable(name = "user_id") userId: Long,
    ): CommonResponse<UserPointResponse> {
        return CommonResponse.ok()
    }
}