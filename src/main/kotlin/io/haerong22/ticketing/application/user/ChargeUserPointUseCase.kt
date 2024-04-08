package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.user.command.ChargeUserPointCommand
import io.haerong22.ticketing.domain.user.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChargeUserPointUseCase(
    private val userReader: UserReader,
    private val userModifier: UserModifier,
    private val userPointHistoryAppender: UserPointHistoryAppender,
) {

    operator fun invoke(command: ChargeUserPointCommand) : User {
        val user = userReader.getUserByIdWithLock(command.userId)
        val updated = userModifier.updateUserPoint(user, command.amount)
        userPointHistoryAppender.append(user, command.amount)
        return updated
    }
}