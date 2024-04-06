package io.haerong22.ticketing.api.user.controller

import io.haerong22.ticketing.api.WebTestSupport
import io.haerong22.ticketing.application.user.GetUserPointUseCase
import io.haerong22.ticketing.application.user.command.GetUserPointCommand
import io.haerong22.ticketing.domain.user.User
import io.haerong22.ticketing.domain.user.UserPoint
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserPointController::class)
class UserPointControllerTest() : WebTestSupport() {

    @MockBean
    lateinit var getUserPointUseCase: GetUserPointUseCase

    @Test
    fun `유저 포인트 조회 성공`() {
        // given
        val userId = 1L
        given(getUserPointUseCase(GetUserPointCommand(userId)))
            .willReturn(User(userId, "유저1", UserPoint(0)))

        // then
        mockMvc.perform(
            get("/api/users/%d/point".format(userId))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0])
    fun `유저 포인트 조회 시 userId 는 양수이다`(userId: Long) {
        // given

        // then
        mockMvc.perform(
            get("/api/users/%d/point".format(userId))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.message").value("userId는 양수 값 입니다."))
    }
}