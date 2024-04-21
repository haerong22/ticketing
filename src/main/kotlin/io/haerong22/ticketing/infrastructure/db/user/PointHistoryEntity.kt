package io.haerong22.ticketing.infrastructure.db.user

import io.haerong22.ticketing.domain.common.enums.TransactionType
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPointHistory
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "point_history")
class PointHistoryEntity(
    val userId: Long,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    val amount: Int,
) : BaseEntity() {

    companion object {

        fun of(user: User, userPointHistory: UserPointHistory): PointHistoryEntity {
            return PointHistoryEntity(
                userId = user.userId,
                type = userPointHistory.type,
                amount = userPointHistory.amount,
            )
        }
    }

    fun toDomain(): UserPointHistory {
        return UserPointHistory(
            amount = amount,
            type = type,
        )
    }
}
