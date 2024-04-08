package io.haerong22.ticketing.domain.user

interface UserReaderRepository {

    fun getUserById(userId: Long) : User

    fun getUserByIdWithLock(userId: Long) : User
}