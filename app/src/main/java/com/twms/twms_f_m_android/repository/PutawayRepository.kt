package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow

interface PutawayRepository {
    fun getAllPutaway(): Flow<Resource<CustomResponse<List<Putaway>>>>
    fun acknowledge(putawayId: Int): Flow<Resource<CustomResponse<Putaway>>>
    fun reject(putawayId: Int): Flow<Resource<CustomResponse<Putaway>>>
    fun putaway(putawayId: Int): Flow<Resource<CustomResponse<String>>>
}