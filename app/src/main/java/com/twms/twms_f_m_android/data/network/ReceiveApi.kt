package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Receive
import retrofit2.http.*

interface ReceiveApi {
    @POST("Receiving/Receive")
    suspend fun receive(@Body receive: Receive): CustomResponse<String>
}