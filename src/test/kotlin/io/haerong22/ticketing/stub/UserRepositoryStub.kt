package io.haerong22.ticketing.stub

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.domain.user.UserRepository

class UserRepositoryStub : UserRepository {

    override fun getUserById(userId: Long): User {
        return User(1L, "유저1", UserPoint(10000))
    }
}