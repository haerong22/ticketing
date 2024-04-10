package io.haerong22.ticketing.domain.user

import io.haerong22.ticketing.domain.common.enums.TransactionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userReaderRepository: UserReaderRepository,
    private val userStoreRepository: UserStoreRepository,
) {

    fun getUser(userId: Long) : User {
        return userReaderRepository.getUserById(userId)
    }

    @Transactional
    fun chargePoint(userId: Long, amount: Int) : User {
        val user = userReaderRepository.getUserByIdWithPessimisticLock(userId)

        val newUser = user.chargePoint(amount)
        userStoreRepository.saveUser(newUser)

        val pointHistory = UserPointHistory(amount, TransactionType.CHARGE)
        userStoreRepository.saveUserPointHistory(user, pointHistory)

        return newUser
    }
}