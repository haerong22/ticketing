package io.haerong22.ticketing.infrastructure.db.user

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from UserEntity u where u.id=:userId")
    fun findByIdForUpdate(userId: Long): Optional<UserEntity>
}