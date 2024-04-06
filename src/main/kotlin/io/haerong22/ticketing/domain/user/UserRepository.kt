package io.haerong22.ticketing.domain.user

interface UserRepository {

    fun getUserById(userId: Long) : User
}