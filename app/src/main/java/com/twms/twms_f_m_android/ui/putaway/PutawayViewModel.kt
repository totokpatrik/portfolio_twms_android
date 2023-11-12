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
class PutawayViewModel
@Inject
constructor(
    private val putawayRepository: PutawayRepository
): ViewModel() {
    private val _putawayResponse = MutableLiveData<Resource<CustomResponse<List<Putaway>>>>()
    val putawayResponse = _putawayResponse

    private val _putawayAcknowledge = MutableLiveData<Resource<CustomResponse<Putaway>>>()
    val putawayAcknowledge = _putawayAcknowledge

    fun getAllPutaway() {
        viewModelScope.launch {
            putawayRepository
                .getAllPutaway()
                .collect {
                    _putawayResponse.value = it
                }
        }
    }

    fun acknowledge(putawayId: Int) {
        viewModelScope.launch {
            putawayRepository
                .acknowledge(putawayId)
                .collect{
                    _putawayAcknowledge.value = it
                }
        }
    }
}