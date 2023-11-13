package com.twms.twms_f_m_android.repository



import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Receive
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow


interface ReceiveRepository {
    fun receive(receive: Receive): Flow<Resource<CustomResponse<String>>>
}