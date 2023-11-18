package com.twms.twms_f_m_android.ui.inbound_shipment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.repository.InboundShipmentRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InboundShipmentViewModel
@Inject
constructor(
    private val inboundShipmentRepository: InboundShipmentRepository
): ViewModel() {
    private val _inboundShipmentResponse = MutableLiveData<Resource<CustomResponse<List<ReceiveLine>>>>()
    val inboundShipmentResponse = _inboundShipmentResponse

    private val _inboundShipmentCloseResponse = MutableLiveData<Resource<CustomResponse<String>>>()
    val inboundShipmentCloseResponse = _inboundShipmentCloseResponse

    fun getShipment(id: String) {
        viewModelScope.launch {
            inboundShipmentRepository
                .getShipment(id)
                .collect { _inboundShipmentResponse.value = it  }
        }
    }

    fun closeShipment(inboundShipmentId: Number) {
        viewModelScope.launch {
            inboundShipmentRepository
                .closeShipment(inboundShipmentId)
                .collect { inboundShipmentCloseResponse.value = it  }
        }
    }
}