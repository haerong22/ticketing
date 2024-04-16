package io.haerong22.ticketing.infrastructure.db.reservation

import io.haerong22.ticketing.domain.common.enums.PaymentStatus
import io.haerong22.ticketing.domain.reservation.Payment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PaymentEntityTest {

    @Test
    fun `payment 도메인으로 PaymentEntity를 생성한다`() {
        // given
        val payment = Payment(
            paymentId = 1L,
            reservationId = 1L,
            price = 10000,
            status = PaymentStatus.COMPLETE
        )

        // when
        val result = PaymentEntity.of(payment)

        // then
        assertThat(result.id).isEqualTo(1L)
        assertThat(result.reservationId).isEqualTo(1L)
        assertThat(result.price).isEqualTo(10000)
        assertThat(result.status).isEqualTo(PaymentStatus.COMPLETE)
    }
}