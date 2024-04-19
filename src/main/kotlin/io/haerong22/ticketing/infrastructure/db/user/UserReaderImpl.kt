package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserReader
import org.springframework.stereotype.Repository

@Repository
class UserReaderImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserReader {

    override fun getUser(userId: Long): User? {
        return userJpaRepository.findById(userId).orElse(null)
            ?.toDomain()
    }

    override fun getUserWithPessimisticLock(userId: Long): User? {
        return userJpaRepository.findByIdForUpdate(userId)
            ?.toDomain()
    }
}