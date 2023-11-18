package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Load
import retrofit2.http.*
import java.util.UUID

interface LoadingApi {
    @GET("Loads")
    suspend fun get(): CustomResponse<List<Load>>
    @GET("Loads/GetSingle")
    suspend fun getSingle(@Query("loadId")loadId: Number): CustomResponse<Load>
    @POST("Loads/Acknowledge")
    suspend fun acknowledge(@Query("loadId")loadId: Number): CustomResponse<Load>
    @POST("Loads/Load")
    suspend fun load(@Query("inventoryId")inventoryId: UUID): CustomResponse<Load>
    @POST("Loads/Complete")
    suspend fun complete(@Query("loadId")loadId: Number): CustomResponse<Load>
}