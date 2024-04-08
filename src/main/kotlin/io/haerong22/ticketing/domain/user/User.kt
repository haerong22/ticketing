package io.haerong22.ticketing.domain.user

class User(
    val userId: Long,
    val name: String,
    val point: UserPoint,
) {

    fun chargeUserPoint(amount: Int): User {
        return User(
            userId = userId,
            name = name,
            point = point.charge(amount)
        )
    }
}