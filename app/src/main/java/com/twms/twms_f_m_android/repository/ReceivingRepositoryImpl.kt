package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.data.network.ReceivingApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReceivingRepositoryImpl
@Inject
constructor(
    private val api: ReceivingApi
): ReceivingRepository {
    override fun getAllReceivingInboundShipment(): Flow<Resource<CustomResponse<List<InboundShipment>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getAllReceivingInboundShipment()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun acknowledge(inboundShipmentId: Number): Flow<Resource<CustomResponse<String>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.acknowledge(inboundShipmentId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
}