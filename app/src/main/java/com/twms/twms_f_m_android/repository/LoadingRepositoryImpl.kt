package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Load
import com.twms.twms_f_m_android.data.network.LoadingApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class LoadingRepositoryImpl
@Inject
constructor(
    private val api: LoadingApi
): LoadingRepository {
    override fun get(): Flow<Resource<CustomResponse<List<Load>>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.get()))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun getSingle(loadId: Number): Flow<Resource<CustomResponse<Load>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getSingle(loadId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

    override fun acknowledge(loadId: Number): Flow<Resource<CustomResponse<Load>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.acknowledge(loadId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
    override fun load(inventoryId: UUID): Flow<Resource<CustomResponse<Load>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.load(inventoryId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
    override fun complete(loadId: Number): Flow<Resource<CustomResponse<Load>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.complete(loadId)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
}