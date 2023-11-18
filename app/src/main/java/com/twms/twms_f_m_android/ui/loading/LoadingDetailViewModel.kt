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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoadingDetailViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
) : ViewModel() {
    private val _getSingleResponse = MutableLiveData<Resource<CustomResponse<Load>>>()
    val getResponse = _getSingleResponse

    private val _loadResponse = MutableLiveData<Resource<CustomResponse<Load>>>()
    val loadResponse = _loadResponse

    private val _loadCompleteResponse = MutableLiveData<Resource<CustomResponse<Load>>>()
    val loadCompleteResponse = _loadCompleteResponse

    fun getSingle(loadId: Number) {
        viewModelScope.launch {
            loadingRepository
                .getSingle(loadId)
                .collect { _getSingleResponse.value = it }
        }
    }

    fun load(inventoryId: UUID) {
        viewModelScope.launch {
            loadingRepository
                .load(inventoryId)
                .collect { _loadResponse.value = it }
        }
    }

    fun complete(loadId: Number) {
        viewModelScope.launch {
            loadingRepository
                .complete(loadId)
                .collect { _loadCompleteResponse.value = it }
        }
    }
}