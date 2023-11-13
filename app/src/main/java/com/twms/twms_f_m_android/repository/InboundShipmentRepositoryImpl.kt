package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.data.network.InboundShipmentApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InboundShipmentRepositoryImpl
@Inject
constructor(
    private val api: InboundShipmentApi
): InboundShipmentRepository {
    override fun getShipment(id: String): Flow<Resource<CustomResponse<List<ReceiveLine>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getShipment(id)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun closeShipment(id: Number): Flow<Resource<CustomResponse<String>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.closeShipment(id)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}