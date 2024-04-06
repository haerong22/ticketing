package io.haerong22.ticketing.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository,
) {

    fun getUserById(userId: Long) : User {
        return userRepository.getUserById(userId)
    }
}