package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.OutboundShipment
import com.twms.twms_f_m_android.data.network.OutboundShipmentApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OutboundShipmentRepositoryImpl
@Inject
constructor(
    private val api: OutboundShipmentApi
): OutboundShipmentRepository {
    override fun getPickingShipments(): Flow<Resource<CustomResponse<List<OutboundShipment>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getPickingShipments()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun getShipmentById(outboundShipmentId: Number): Flow<Resource<CustomResponse<OutboundShipment>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getShipmentById(outboundShipmentId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }


}