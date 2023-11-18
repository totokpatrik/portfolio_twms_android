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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PickingFinishViewModel @Inject constructor  (
    private val pickingRepository: PickingRepository,
    private val outboundShipmentRepository: OutboundShipmentRepository
) : ViewModel() {

    private val _shipmentResponse = MutableLiveData<Resource<CustomResponse<OutboundShipment>>>()
    val shipmentResponse = _shipmentResponse

    private val _finishPickResponse = MutableLiveData<Resource<CustomResponse<Pick>>>()
    val finishPickResponse = _finishPickResponse

    fun getShipmentById(outboundShipmentId: Number) {
        viewModelScope.launch {
            outboundShipmentRepository
                .getShipmentById(outboundShipmentId)
                .collect { _shipmentResponse.value = it  }
        }
    }

    fun finishPick(inventoryId: UUID, locationId: Number, outboundShipmentId: Number) {
        viewModelScope.launch {
            pickingRepository
                .finish(inventoryId, locationId, outboundShipmentId)
                .collect { finishPickResponse.value = it  }
        }
    }


}