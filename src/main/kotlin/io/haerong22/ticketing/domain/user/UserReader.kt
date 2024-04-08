package io.haerong22.ticketing.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userReaderRepository: UserReaderRepository,
) {

    fun getUserById(userId: Long) : User {
        return userReaderRepository.getUserById(userId)
    }

    fun getUserByIdWithLock(userId: Long) : User {
        return userReaderRepository.getUserByIdWithLock(userId)
    }
}