package com.twms.twms_f_m_android.repository

import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.network.Api
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val api: Api
): AuthRepository {
    override fun login(auth: Auth): Flow<Resource<CustomResponse<AuthResponse>>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.login(auth)))
        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}