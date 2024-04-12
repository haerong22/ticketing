package io.haerong22.ticketing.stub

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserStore

class UserStoreStub : UserStore {

    override fun saveUser(user: User): User {
        return user
    }

    override fun savePointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        return userPointHistory
    }
}