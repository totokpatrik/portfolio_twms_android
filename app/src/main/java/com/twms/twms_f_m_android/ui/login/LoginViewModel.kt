package com.twms.twms_f_m_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.repository.AuthRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _loginResponse : MutableLiveData<Resource<CustomResponse<AuthResponse>>> = MutableLiveData()
    val loginResponse : LiveData<Resource<CustomResponse<AuthResponse>>> = _loginResponse

    fun login(auth: Auth) {
        viewModelScope.launch {
            authRepository
                .login(auth)
                .collect { _loginResponse.value = it  }
        }
    }

}