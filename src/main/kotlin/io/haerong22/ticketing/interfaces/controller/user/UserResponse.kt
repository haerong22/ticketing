package io.haerong22.ticketing.interfaces.controller.user

import io.haerong22.ticketing.domain.user.User

class UserResponse {

    class Point(
        val userId: Long,
        val userName: String,
        val point: Int,
    ) {
        companion object {

            fun toResponse(user: User): Point {
                return Point(
                    userId = user.userId,
                    userName = user.name,
                    point = user.point.amount,
                )
            }
        }
    }
}
