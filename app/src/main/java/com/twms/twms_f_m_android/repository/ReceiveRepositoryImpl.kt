package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Receive
import com.twms.twms_f_m_android.data.network.ReceiveApi
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReceiveRepositoryImpl
@Inject
constructor(
    private val api: ReceiveApi
): ReceiveRepository {
    override fun receive(receive: Receive): Flow<Resource<CustomResponse<String>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.receive(receive)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }
}