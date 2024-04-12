package io.haerong22.ticketing.domain.user

interface UserStore {

    fun saveUser(user: User) : User

    fun savePointHistory(user: User, userPointHistory: UserPointHistory) : UserPointHistory
}