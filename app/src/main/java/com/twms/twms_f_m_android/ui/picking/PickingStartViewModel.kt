package com.twms.twms_f_m_android.ui.picking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Inventory
import com.twms.twms_f_m_android.repository.PickingRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickingStartViewModel @Inject constructor  (
    private val pickingRepository: PickingRepository
) : ViewModel() {
    private val _startResponse = MutableLiveData<Resource<CustomResponse<Inventory>>>()
    val startResponse = _startResponse

    fun start(palletNumber: String) {
        viewModelScope.launch {
            pickingRepository
                .start(palletNumber)
                .collect { _startResponse.value = it  }
        }
    }
}