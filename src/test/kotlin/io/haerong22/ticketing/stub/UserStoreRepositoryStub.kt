package io.haerong22.ticketing.stub

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserStoreRepository

class UserStoreRepositoryStub : UserStoreRepository {

    override fun saveUser(user: User): User {
        return user
    }

    override fun saveUserPointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        return userPointHistory
    }
}