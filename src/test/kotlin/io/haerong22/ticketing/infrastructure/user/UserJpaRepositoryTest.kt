package io.haerong22.ticketing.infrastructure.user

import io.haerong22.ticketing.infrastructure.db.user.UserJpaRepository
import org.junit.jupiter.api.Test

class UserJpaRepositoryTest(
    private val userJpaRepository: UserJpaRepository,
) : DbTestSupport() {

    @Test
    fun connection() {
        userJpaRepository.count()
    }
}