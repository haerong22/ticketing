package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.infrastructure.DbTestSupport
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class UserJpaRepositoryTest(
    private val userJpaRepository: UserJpaRepository,
    private val em: EntityManager,
) : DbTestSupport() {

    @Test
    fun connection() {
        userJpaRepository.count()
    }


    @Test
    fun `유저 조회 시 락을 획득한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))
        val userId = 1L

        // when
        val result = userJpaRepository.findByIdForUpdate(userId)

        // then
        Assertions.assertThat(em.getLockMode(result)).isEqualTo(LockModeType.PESSIMISTIC_WRITE)
    }
}