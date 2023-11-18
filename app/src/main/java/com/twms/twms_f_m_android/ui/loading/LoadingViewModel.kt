package com.twms.twms_f_m_android.ui.loading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Load
import com.twms.twms_f_m_android.repository.LoadingRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
) : ViewModel() {
    private val _getResponse = MutableLiveData<Resource<CustomResponse<List<Load>>>>()
    val getResponse = _getResponse

    private val _acknowledgeResponse = MutableLiveData<Resource<CustomResponse<Load>>>()
    val acknowledgeResponse = _acknowledgeResponse
    fun get() {
        viewModelScope.launch {
            loadingRepository
                .get()
                .collect { _getResponse.value = it  }
        }
    }
    fun acknowledge(loadId: Number) {
        viewModelScope.launch {
            loadingRepository
                .acknowledge(loadId)
                .collect { _acknowledgeResponse.value = it  }
        }
    }
}