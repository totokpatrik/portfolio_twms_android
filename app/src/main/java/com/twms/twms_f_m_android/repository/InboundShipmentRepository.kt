package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow


interface InboundShipmentRepository {
    fun getShipment(id: String): Flow<Resource<CustomResponse<List<ReceiveLine>>>>
    fun closeShipment(id: Number): Flow<Resource<CustomResponse<String>>>
}