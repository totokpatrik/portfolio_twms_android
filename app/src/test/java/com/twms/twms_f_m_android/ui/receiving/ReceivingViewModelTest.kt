package com.twms.twms_f_m_android.ui.receiving

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.twms.twms_f_m_android.MainCoroutinesRule
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.repository.ReceivingRepository
import com.twms.twms_f_m_android.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class
ReceivingViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: ReceivingViewModel

    @Mock
    lateinit var receivingResponse: CustomResponse<List<InboundShipment>>

    @Mock
    lateinit var repository: ReceivingRepository

    @Mock
    lateinit var receivingResponseObserver: Observer<Resource<CustomResponse<List<InboundShipment>>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ReceivingViewModel(repository)
    }

    @Test
    fun `return ok when get all receive successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(receivingResponse))
            }

            viewModel.receivingResponse.observeForever(receivingResponseObserver)

            BDDMockito.given(repository.getAllReceivingInboundShipment()).willReturn(flow)

            viewModel.getAllReceivingInboundShipment()

            Assert.assertNotNull(viewModel.receivingResponse.value)
        }
    }

    @Test
    fun `empty data when get all receive not successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.receivingResponse.observeForever(receivingResponseObserver)

            BDDMockito.given(repository.getAllReceivingInboundShipment()).willReturn(flow)

            viewModel.getAllReceivingInboundShipment()

            Assert.assertNull(viewModel.receivingResponse.value?.data)
        }
    }
}