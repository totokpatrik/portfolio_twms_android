package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.data.network.PutawayApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PutawayRepositoryImpl
@Inject
constructor(
    private val api: PutawayApi
): PutawayRepository {
    override fun getAllPutaway(): Flow<Resource<CustomResponse<List<Putaway>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getAllPutaway()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun acknowledge(putawayId: Int): Flow<Resource<CustomResponse<Putaway>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.acknowledge(putawayId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun reject(putawayId: Int): Flow<Resource<CustomResponse<Putaway>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.reject(putawayId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun putaway(putawayId: Int): Flow<Resource<CustomResponse<String>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.putaway(putawayId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
}