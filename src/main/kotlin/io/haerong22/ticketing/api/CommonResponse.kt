package io.haerong22.ticketing.api

import io.haerong22.ticketing.domain.common.CommonResponseCode
import io.haerong22.ticketing.domain.common.ResponseCode

data class CommonResponse<T>(
    var code: Int,
    val message: String,
    val body: T? = null,
) {

    companion object {

        @JvmStatic
        fun <T> ok(): CommonResponse<T> {
            return process(CommonResponseCode.SUCCESS.code, CommonResponseCode.SUCCESS.msg, null)
        }

        @JvmStatic
        fun <T> ok(body: T): CommonResponse<T> {
            return process(0, "success", body)
        }

        @JvmStatic
        fun <T> error(responseCode: ResponseCode): CommonResponse<T> {
            return process(responseCode.code, responseCode.msg, null)
        }

        @JvmStatic
        fun <T> error(responseCode: ResponseCode, message: String): CommonResponse<T> {
            return process(responseCode.code, message, null)
        }

        @JvmStatic
        private fun <T> process(code: Int, message: String, body: T?): CommonResponse<T> {
            return CommonResponse(code, message, body)
        }
    }
}