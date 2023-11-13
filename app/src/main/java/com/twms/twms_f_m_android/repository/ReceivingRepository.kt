package com.twms.twms_f_m_android.repository



import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow


interface ReceivingRepository {
    fun getAllReceivingInboundShipment() : Flow<Resource<CustomResponse<List<InboundShipment>>>>
}