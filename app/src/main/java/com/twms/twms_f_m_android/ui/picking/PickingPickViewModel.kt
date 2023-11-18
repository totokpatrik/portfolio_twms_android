package com.twms.twms_f_m_android.ui.picking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Pick
import com.twms.twms_f_m_android.repository.PickingRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PickingPickViewModel @Inject constructor  (
    private val pickingRepository: PickingRepository
) : ViewModel() {
    private val _nextPickResponse = MutableLiveData<Resource<CustomResponse<Pick>>>()
    val nextPickResponse = _nextPickResponse

    private val _pickResponse = MutableLiveData<Resource<CustomResponse<String>>>()
    val pickResponse = _pickResponse
    fun getNextPick(outboundshipmentId: Number) {
        viewModelScope.launch {
            pickingRepository
                .getNextPick(outboundshipmentId)
                .collect { _nextPickResponse.value = it  }
        }
    }
    fun pick(pickId: Number, inventoryId: UUID, quantity: Number) {
        viewModelScope.launch {
            pickingRepository
                .pick(pickId, inventoryId, quantity)
                .collect { _pickResponse.value = it  }
        }
    }

}