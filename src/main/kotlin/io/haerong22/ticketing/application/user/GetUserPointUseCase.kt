package io.haerong22.ticketing.application.user

import io.haerong22.ticketing.application.user.command.UserCommand
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserReader
import org.springframework.stereotype.Service

@Service
class GetUserPointUseCase(
    private val userReader: UserReader,
) {

    operator fun invoke(command: UserCommand.GetPoint): User {
        return userReader.getUser(command.userId)
    }
}