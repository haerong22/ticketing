package io.haerong22.ticketing.domain.user

data class User(
    val userId: Long,
    val name: String,
    val point: UserPoint,
) {

    fun chargePoint(amount: Int): User {
        return this.copy(point = point.charge(amount))
    }
}