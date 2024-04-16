package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.PaymentStatus

data class Payment(
    val paymentId: Long = 0,
    val reservationId: Long,
    val price: Int,
    val status: PaymentStatus,
) {

    companion object {

        fun pay(reservationId: Long, price: Int): Payment {
            return Payment(
                reservationId = reservationId,
                price = price,
                status = PaymentStatus.COMPLETE,
            )
        }
    }
}