package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.enums.TransactionType
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader,
    private val userStore: UserStore,
) {

    fun getUser(userId: Long) : User {
        return userReader.getUser(userId)
    }

    fun getUserWithLock(userId: Long) : User {
        return userReader.getUserWithPessimisticLock(userId)
    }

    fun chargePoint(user: User, amount: Int) : User {
        val charged = user.chargePoint(amount)
        userStore.saveUser(charged)

        val pointHistory = UserPointHistory(amount, TransactionType.CHARGE)
        userStore.savePointHistory(charged, pointHistory)

        return charged
    }
}