package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.PaymentStatus
import io.haerong22.ticketing.domain.reservation.Payment
import io.haerong22.ticketing.infrastructure.db.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "payment")
class PaymentEntity(
    val reservationId: Long,
    val price: Int,
    val status: PaymentStatus,
) : BaseEntity() {

    companion object {

        fun of(payment: Payment): PaymentEntity {
            return PaymentEntity(
                reservationId = payment.reservationId,
                price = payment.price,
                status = payment.status,
            ).apply { id = payment.paymentId }
        }
    }

    fun toDomain() : Payment {
        return Payment(
            paymentId = id!!,
            reservationId = reservationId,
            price = price,
            status = status,
        )
    }
}