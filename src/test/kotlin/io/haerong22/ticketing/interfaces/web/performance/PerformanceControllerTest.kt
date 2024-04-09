package io.haerong22.ticketing.interfaces.web.performance

import io.haerong22.ticketing.application.performance.GetPerformanceInfoListUseCase
import io.haerong22.ticketing.domain.common.PageInfo
import io.haerong22.ticketing.domain.common.WithPage
import io.haerong22.ticketing.interfaces.web.WebTestSupport
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PerformanceController::class)
class PerformanceControllerTest() : WebTestSupport() {

    @MockBean
    lateinit var getPerformanceInfoListUseCase: GetPerformanceInfoListUseCase

    @Test
    fun `공연 리스트 조회 성공`() {
        // given
        val token = "4844c369-717f-4730-8b4f-c3a890094daa"
        val pageNo = 1
        val pageSize = 2
        val pageInfo = PageInfo(pageNo, pageSize, 2)

        given(getPerformanceInfoListUseCase(any()))
            .willReturn(WithPage(listOf(), pageInfo))

        // then
        mockMvc.perform(
            get("/api/performances")
                .param("page_no", pageNo.toString())
                .param("page_size", pageSize.toString())
                .header("wq-token", token)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value("0"))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.body.performances").isArray)
    }
}