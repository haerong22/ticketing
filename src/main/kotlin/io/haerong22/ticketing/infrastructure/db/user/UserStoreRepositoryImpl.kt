package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserStoreRepository
import org.springframework.stereotype.Repository

@Repository
class UserStoreRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserStoreRepository {

    override fun saveUser(user: User): User {
        TODO("Not yet implemented")
    }

    override fun saveUserPointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        TODO("Not yet implemented")
    }
}