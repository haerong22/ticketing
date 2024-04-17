package io.haerong22.ticketing.interfaces.web.queue

import io.haerong22.ticketing.application.queue.EnterWaitingQueueUseCase
import io.haerong22.ticketing.domain.common.enums.QueueStatus
import io.haerong22.ticketing.domain.queue.WaitingQueue
import io.haerong22.ticketing.interfaces.web.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(QueueController::class)
class QueueControllerTest : WebTestSupport() {

    @MockBean
    lateinit var enterWaitingQueueUseCase: EnterWaitingQueueUseCase

    @Test
    fun `대기열 입장 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val expected = WaitingQueue(
            queueId = 1L,
            token = token,
            rank = 10,
            status = QueueStatus.WAITING,
            expiredAt = null
        )

        given(enterWaitingQueueUseCase()).willReturn(expected)

        // then
        mockMvc.perform(
            post("/api/queue/enter")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.token").value("4844c369-717f-4730-8b4f-c3a890094daa"))
            .andExpect(jsonPath("$.body.rank").value(10))
            .andExpect(jsonPath("$.body.status").value("WAITING"))
    }
}