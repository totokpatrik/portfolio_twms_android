package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.OutboundShipment
import retrofit2.http.*

interface OutboundShipmentApi {
    @GET("Outbound/OutboundShipments/GetPickingShipments")
    suspend fun getPickingShipments():
            CustomResponse<List<OutboundShipment>>

    @GET("Outbound/OutboundShipments/{id}")
    suspend fun getShipmentById(@Path("id") outboundShipmentId: Number):
            CustomResponse<OutboundShipment>
}
