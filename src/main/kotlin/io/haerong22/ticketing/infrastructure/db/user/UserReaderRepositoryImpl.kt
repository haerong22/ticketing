package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserException
import io.haerong22.ticketing.domain.user.UserReaderRepository
import io.haerong22.ticketing.domain.user.UserResponseCode.NOT_FOUND_USER
import org.springframework.stereotype.Repository

@Repository
class UserReaderRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserReaderRepository {

    override fun getUserById(userId: Long): User {
        return userJpaRepository.findById(userId)
            .orElseThrow { throw UserException(NOT_FOUND_USER) }
            .toDomain()
    }

    override fun getUserByIdWithLock(userId: Long): User {
        TODO("Not yet implemented")
    }
}