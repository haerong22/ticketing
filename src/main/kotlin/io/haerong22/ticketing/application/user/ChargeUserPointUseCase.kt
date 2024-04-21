package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChargeUserPointUseCase(
    private val userService: UserService,
) {

    operator fun invoke(command: UserCommand.ChargePoint): User {
        val user = userService.getUserWithLock(command.userId)
        return userService.chargePoint(user, command.amount)
    }
}
