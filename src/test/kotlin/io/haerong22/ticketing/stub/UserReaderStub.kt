package io.haerong22.ticketing.stub

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.domain.user.UserReader

class UserReaderStub : UserReader {

    override fun getUser(userId: Long): User {
        return User(1L, "유저1", UserPoint(10000))
    }

    override fun getUserWithPessimisticLock(userId: Long): User {
        return User(1L, "유저1", UserPoint(10000))
    }

}