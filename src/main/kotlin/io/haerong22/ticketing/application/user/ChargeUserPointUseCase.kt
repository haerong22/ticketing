package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class ChargeUserPointUseCase(
    private val userService: UserService,
) {

    operator fun invoke(command: UserCommand.ChargePoint) : User {
        return userService.chargePoint(command.userId, command.amount)
    }
}