package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.OutboundShipment
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow


interface OutboundShipmentRepository {
    fun getPickingShipments(): Flow<Resource<CustomResponse<List<OutboundShipment>>>>
    fun getShipmentById(outboundShipmentId: Number): Flow<Resource<CustomResponse<OutboundShipment>>>
}