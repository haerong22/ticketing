package io.haerong22.ticketing.domain.user

import org.springframework.stereotype.Component

@Component
class UserModifier(
    private val userStoreRepository: UserStoreRepository
) {

    fun updateUserPoint(user: User, amount: Int): User {
        val newUser = user.chargeUserPoint(amount)
        return userStoreRepository.saveUser(newUser)
    }
}