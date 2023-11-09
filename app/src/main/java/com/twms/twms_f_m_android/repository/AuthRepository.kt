package com.twms.twms_f_m_android.repository

import androidx.paging.PagingData
import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface AuthRepository {
    fun login(auth: Auth): Flow<Resource<CustomResponse<AuthResponse>>>
}