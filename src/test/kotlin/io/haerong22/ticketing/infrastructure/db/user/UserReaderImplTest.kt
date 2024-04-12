package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.UserException
import io.haerong22.ticketing.infrastructure.DbTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import

@Import(UserReaderImpl::class)
class UserReaderImplTest(
    private val userRepositoryImpl: UserReaderImpl,
    private val userJpaRepository: UserJpaRepository,
) : DbTestSupport() {

    @Test
    fun `유저를 조회한다`() {
        // given
        userJpaRepository.save(UserEntity("유저", 0))
        val userId = 1L

        // when
        val result = userRepositoryImpl.getUser(userId)

        // then
        assertThat(result.userId).isEqualTo(1)
        assertThat(result.name).isEqualTo("유저")
        assertThat(result.point.amount).isEqualTo(0)
    }

    @Test
    fun `유저 조회 시 해당 유저가 없으면 UserException 이 발생한다`() {
        // given
        val userId = 1L

        // when, then
        assertThatThrownBy { userRepositoryImpl.getUser(userId) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("유저를 찾을 수 없습니다.")
    }
}