package com.twms.twms_f_m_android.data.network

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Inventory
import com.twms.twms_f_m_android.data.model.Pick
import retrofit2.http.*
import java.util.UUID

interface PickingApi {
    @POST("Picks/Acknowledge")
    suspend fun acknowledge(@Query("outboundShipmentId")outboundShipmentId: Number):
            CustomResponse<List<Pick>>

    @POST("Picks/Pick")
    suspend fun pick(
        @Query("pickId")pickId: Number,
        @Query("inventoryId")inventoryId: UUID,
        @Query("quantity")quantity: Number):
            CustomResponse<String>

    @POST("Picks/Finish")
    suspend fun finish(@Query("inventoryId")inventoryId: UUID, @Query("locationId")locationId: Number, @Query("outboundShipmentId")outboundShipmentId: Number):
            CustomResponse<Pick>
    @POST("Picks/Start")
    suspend fun start(@Query("palletNumber")palletNumber: String):
            CustomResponse<Inventory>

    @GET("Picks/GetNextPick")
    suspend fun getNextPick(@Query("outboundShipmentId")outboundShipmentId: Number):
            CustomResponse<Pick>

}