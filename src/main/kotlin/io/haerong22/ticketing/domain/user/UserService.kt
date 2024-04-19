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
            ?: throw UserException(UserResponseCode.NOT_FOUND_USER)
    }

    fun getUserWithLock(userId: Long) : User {
        return userReader.getUserWithPessimisticLock(userId)
            ?: throw UserException(UserResponseCode.NOT_FOUND_USER)
    }

    fun chargePoint(user: User, amount: Int) : User {
        val charged = user.chargePoint(amount)
        userStore.saveUser(charged)

        val pointHistory = UserPointHistory(amount, TransactionType.CHARGE)
        userStore.savePointHistory(charged, pointHistory)

        return charged
    }

    fun usePoint(user: User, amount: Int) : User {
        val used = user.usePoint(amount)
        userStore.saveUser(used)

        val pointHistory = UserPointHistory(amount, TransactionType.USE)
        userStore.savePointHistory(used, pointHistory)

        return used
    }
}