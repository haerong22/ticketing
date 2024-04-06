package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    val name: String,
    val point: Int,
) : BaseEntity() {

}