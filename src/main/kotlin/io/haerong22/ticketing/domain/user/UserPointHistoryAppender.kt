package io.haerong22.ticketing.domain.user

import org.springframework.stereotype.Component

@Component
class UserPointHistoryAppender(
    private val userStoreRepository: UserStoreRepository,
) {

    fun append(user: User, amount: Int) {
        val userPointHistory = UserPointHistory.createChargeHistory(amount)
        userStoreRepository.saveUserPointHistory(user, userPointHistory)
    }
}