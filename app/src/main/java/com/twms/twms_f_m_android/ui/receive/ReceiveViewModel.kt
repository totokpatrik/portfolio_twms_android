package com.twms.twms_f_m_android.ui.receive

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Receive
import com.twms.twms_f_m_android.repository.ReceiveRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveViewModel @Inject constructor  (
    private val receiveRepository: ReceiveRepository
) : ViewModel() {
    private val _receiveResponse = MutableLiveData<Resource<CustomResponse<String>>>()
    val receiveResponse = _receiveResponse

    fun receive(receive: Receive) {
        viewModelScope.launch {
            receiveRepository
                .receive(receive)
                .collect { _receiveResponse.value = it  }
        }
    }
}