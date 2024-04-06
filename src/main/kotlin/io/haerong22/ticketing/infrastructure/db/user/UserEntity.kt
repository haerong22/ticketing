package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    val name: String,
    val point: Int,
) : BaseEntity() {

    fun toDomain() : User{
        return User(
            userId = id!!,
            name = name,
            UserPoint(point),
        )
    }
}