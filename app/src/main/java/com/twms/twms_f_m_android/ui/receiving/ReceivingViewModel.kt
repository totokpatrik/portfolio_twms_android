package com.twms.twms_f_m_android.ui.receiving

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.repository.ReceivingRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceivingViewModel @Inject constructor  (
    private val receivingRepository: ReceivingRepository
) : ViewModel() {
    private val _receivingResponse = MutableLiveData<Resource<CustomResponse<List<InboundShipment>>>>()
    val receivingResponse = _receivingResponse

    fun getAllReceivingInboundShipment() {
        viewModelScope.launch {
            receivingRepository
                .getAllReceivingInboundShipment()
                .collect { _receivingResponse.value = it  }
        }
    }
}