package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.ReceiveLine
import retrofit2.http.*

interface InboundShipmentApi {
    @GET("Receiving/GetReceivingLines")
    suspend fun getShipment(@Query("inboundShipmentId")inboundShipmentId: String):
            CustomResponse<List<ReceiveLine>>

    @POST("Receiving/Close")
    suspend fun closeShipment(@Query("inboundShipmentId")inboundShipmentId: Number):
            CustomResponse<String>
}