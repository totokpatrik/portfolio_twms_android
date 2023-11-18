package com.twms.twms_f_m_android.ui.putaway

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.repository.PutawayRepository
import com.twms.twms_f_m_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PutawayDetailViewModel@Inject
constructor(
    private val putawayRepository: PutawayRepository
): ViewModel() {
    private val _putawayRejectResponse = MutableLiveData<Resource<CustomResponse<Putaway>>>()
    val putawayRejectResponse = _putawayRejectResponse

    private val _putawayResponse = MutableLiveData<Resource<CustomResponse<String>>>()
    val putawayResponse = _putawayResponse

    fun reject(putawayId: Int) {
        viewModelScope.launch {
            putawayRepository
                .reject(putawayId)
                .collect {
                    _putawayRejectResponse.value = it
                }
        }
    }

    fun putaway(putawayId: Int) {
        viewModelScope.launch {
            putawayRepository
                .putaway(putawayId)
                .collect{
                    _putawayResponse.value = it
                }
        }
    }
}