package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Inventory
import com.twms.twms_f_m_android.data.model.Pick
import com.twms.twms_f_m_android.data.network.PickingApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class PickingRepositoryImpl
@Inject
constructor(
    private val api: PickingApi
): PickingRepository {
    override fun acknowledge(outboundShipmentId: Number): Flow<Resource<CustomResponse<List<Pick>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.acknowledge(outboundShipmentId)))
        } catch (e: Exception) {
            emit(Resource.error(e.toString(),null))
        }
    }

    override fun pick(
        pickId: Number,
        inventoryId: UUID,
        quantity: Number
    ): Flow<Resource<CustomResponse<String>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.pick(pickId, inventoryId, quantity)))
        } catch (e: Exception) {
            emit(Resource.error(e.toString(),null))
        }
    }

    override fun finish(
        inventoryId: UUID,
        locationId: Number,
        outboundShipmentId: Number
    ): Flow<Resource<CustomResponse<Pick>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.finish(inventoryId, locationId, outboundShipmentId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun start(palletNumber: String): Flow<Resource<CustomResponse<Inventory>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.start(palletNumber)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun getNextPick(outboundShipmentId: Number): Flow<Resource<CustomResponse<Pick>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getNextPick(outboundShipmentId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}