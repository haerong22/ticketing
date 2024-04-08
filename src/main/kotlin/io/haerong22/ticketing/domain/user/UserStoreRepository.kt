package io.haerong22.ticketing.domain.user

interface UserStoreRepository {

    fun saveUser(user: User) : User

    fun saveUserPointHistory(user: User, userPointHistory: UserPointHistory) : UserPointHistory
}