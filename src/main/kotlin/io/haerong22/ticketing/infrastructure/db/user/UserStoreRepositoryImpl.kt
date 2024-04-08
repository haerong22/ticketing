package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.domain.user.UserStoreRepository
import org.springframework.stereotype.Repository

@Repository
class UserStoreRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
) : UserStoreRepository {

    override fun saveUser(user: User): User {
        val userEntity = UserEntity.of(user)
        return userJpaRepository.save(userEntity).toDomain()
    }

    override fun saveUserPointHistory(user: User, userPointHistory: UserPointHistory): UserPointHistory {
        val pointHistoryEntity = PointHistoryEntity.of(user, userPointHistory)
        return pointHistoryJpaRepository.save(pointHistoryEntity).toDomain()
    }
}