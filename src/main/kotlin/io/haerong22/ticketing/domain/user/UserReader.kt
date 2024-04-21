package io.haerong22.ticketing.domain.user

interface UserReader {

    fun getUser(userId: Long): User?

    fun getUserWithPessimisticLock(userId: Long): User?
}
