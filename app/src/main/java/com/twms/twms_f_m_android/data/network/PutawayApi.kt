package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Putaway
import retrofit2.http.*
interface PutawayApi {
    @GET("Putaway/GetAllPutaway")
    suspend fun getAllPutaway(): CustomResponse<List<Putaway>>
    @POST("Putaway/Acknowledge")
    suspend fun acknowledge(@Query("putawayId")putawayId: Int) : CustomResponse<Putaway>
    @POST("Putaway/Reject")
    suspend fun reject(@Query("putawayId")putawayId: Int) : CustomResponse<Putaway>
    @POST("Putaway")
    suspend fun putaway(@Query("putawayId")putawayId: Int) : CustomResponse<String>
}