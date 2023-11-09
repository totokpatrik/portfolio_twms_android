package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("Auth/login")
    suspend fun login(
        @Body auth: Auth,
    ): CustomResponse<AuthResponse>
}