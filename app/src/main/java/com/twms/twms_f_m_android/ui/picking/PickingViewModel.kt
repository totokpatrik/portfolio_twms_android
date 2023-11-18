package com.twms.twms_f_m_android.ui.picking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.OutboundShipment
import com.twms.twms_f_m_android.data.model.Pick
import com.twms.twms_f_m_android.repository.OutboundShipmentRepository
import com.twms.twms_f_m_android.repository.PickingRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickingViewModel @Inject constructor  (
    private val outboundShipmentRepository: OutboundShipmentRepository,
    private val pickingRepository: PickingRepository
) : ViewModel() {
    private val _outboundShipmentResponse = MutableLiveData<Resource<CustomResponse<List<OutboundShipment>>>>()
    val outboundShipmentResponse = _outboundShipmentResponse

    private val _acknowledgeResponse = MutableLiveData<Resource<CustomResponse<List<Pick>>>>()
    val acknowledgeResponse = _acknowledgeResponse
    fun getPickingShipments() {
        viewModelScope.launch {
            outboundShipmentRepository
                .getPickingShipments()
                .collect { _outboundShipmentResponse.value = it  }
        }
    }
    fun acknowledge(outboundShipmentId: Number) {
        viewModelScope.launch {
            pickingRepository
                .acknowledge(outboundShipmentId)
                .collect { _acknowledgeResponse.value = it  }
        }
    }
}