package io.haerong22.ticketing.api.user.controller.response

import io.haerong22.ticketing.domain.user.User

class UserPointResponse(
    val userId: Long,
    val userName: String,
    val point: Int,
) {
    companion object {

        fun toResponse(user: User) : UserPointResponse {
            return UserPointResponse(
                userId = user.userId,
                userName = user.name,
                point = user.point.amount,
            )
        }
    }
}