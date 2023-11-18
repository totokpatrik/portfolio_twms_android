package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Load
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface LoadingRepository {

    fun get() : Flow<Resource<CustomResponse<List<Load>>>>
    fun getSingle(loadId: Number) : Flow<Resource<CustomResponse<Load>>>
    fun acknowledge(loadId: Number) : Flow<Resource<CustomResponse<Load>>>
    fun load(inventoryId: UUID) : Flow<Resource<CustomResponse<Load>>>
    fun complete(loadId: Number) : Flow<Resource<CustomResponse<Load>>>
}