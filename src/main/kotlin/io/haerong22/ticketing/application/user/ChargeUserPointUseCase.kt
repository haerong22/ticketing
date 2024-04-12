package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.common.enums.TransactionType
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserReader
import io.haerong22.ticketing.domain.user.UserStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChargeUserPointUseCase(
    private val userReader: UserReader,
    private val userStore: UserStore,
) {

    operator fun invoke(command: UserCommand.ChargePoint) : User {
        val user = userReader.getUserWithPessimisticLock(command.userId)

        val charged = user.chargePoint(command.amount)
        userStore.saveUser(charged)

        val pointHistory = UserPointHistory(command.amount, TransactionType.CHARGE)
        userStore.savePointHistory(charged, pointHistory)

        return charged
    }
}