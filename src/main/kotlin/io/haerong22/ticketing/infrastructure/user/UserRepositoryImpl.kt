package io.haerong22.ticketing.infrastructure.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl : UserRepository {

    override fun getUserById(userId: Long): User {
        TODO()
    }
}