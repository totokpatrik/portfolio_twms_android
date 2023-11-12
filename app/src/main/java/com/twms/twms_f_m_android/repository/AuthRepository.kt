package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun login(auth: Auth): Flow<Resource<CustomResponse<AuthResponse>>>
}