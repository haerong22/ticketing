package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.junit.jupiter.api.Test

class UserJpaRepositoryTest(
    private val userJpaRepository: UserJpaRepository,
) : DbTestSupport() {

    @Test
    fun connection() {
        userJpaRepository.count()
    }

}