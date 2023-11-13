package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.InboundShipment
import retrofit2.http.*

interface ReceivingApi {
    @GET("Receiving/GetAllReceivingInboundShipment")
    suspend fun getAllReceivingInboundShipment(): CustomResponse<List<InboundShipment>>
}