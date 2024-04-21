package io.haerong22.ticketing.domain.reservation

import io.haerong22.ticketing.domain.common.enums.PaymentStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PaymentTest {

    @Test
    fun `결제를 생성한다`() {
        // given
        val reservationId = 1L
        val price = 10000

        // when
        val payment = Payment.pay(reservationId = reservationId, price = price)

        // then
        assertThat(payment.paymentId).isEqualTo(0)
        assertThat(payment.reservationId).isEqualTo(1L)
        assertThat(payment.price).isEqualTo(10000)
        assertThat(payment.status).isEqualTo(PaymentStatus.COMPLETE)
    }
}
