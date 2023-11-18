package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Inventory
import com.twms.twms_f_m_android.data.model.Pick
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface PickingRepository {
    fun acknowledge(outboundShipmentId: Number): Flow<Resource<CustomResponse<List<Pick>>>>
    fun pick(pickId: Number, inventoryId: UUID, quantity: Number):
            Flow<Resource<CustomResponse<String>>>
    fun finish(inventoryId: UUID, locationId: Number, outboundShipmentId: Number):
            Flow<Resource<CustomResponse<Pick>>>
    fun start(palletNumber: String): Flow<Resource<CustomResponse<Inventory>>>
    fun getNextPick(outboundShipmentId: Number): Flow<Resource<CustomResponse<Pick>>>
}