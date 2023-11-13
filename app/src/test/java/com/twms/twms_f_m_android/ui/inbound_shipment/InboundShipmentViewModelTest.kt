package com.twms.twms_f_m_android.ui.inbound_shipment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.twms.twms_f_m_android.MainCoroutinesRule
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.repository.InboundShipmentRepository
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
class InboundShipmentViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: InboundShipmentViewModel

    @Mock
    lateinit var inboundShipmentResponse: CustomResponse<List<ReceiveLine>>

    @Mock
    lateinit var inboundShipmentCloseResponse: CustomResponse<String>

    @Mock
    lateinit var repository: InboundShipmentRepository

    @Mock
    lateinit var inboundShipmentResponseObserver: Observer<Resource<CustomResponse<List<ReceiveLine>>>>
    @Mock
    lateinit var inboundShipmentCloseResponseObserver: Observer<Resource<CustomResponse<String>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = InboundShipmentViewModel(repository)
    }

    @Test
    fun `return ok when get shipment successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(inboundShipmentResponse))
            }

            viewModel.inboundShipmentResponse.observeForever(inboundShipmentResponseObserver)

            BDDMockito.given(repository.getShipment("1")).willReturn(flow)

            viewModel.getShipment("1")

            Assert.assertNotNull(viewModel.inboundShipmentResponse.value)
        }
    }

    @Test
    fun `empty data when get shipment not successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.inboundShipmentResponse.observeForever(inboundShipmentResponseObserver)

            BDDMockito.given(repository.getShipment("1")).willReturn(flow)

            viewModel.getShipment("1")

            Assert.assertNull(viewModel.inboundShipmentResponse.value?.data)
        }
    }
    @Test
    fun `return ok when close shipment successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(inboundShipmentCloseResponse))
            }

            viewModel.inboundShipmentCloseResponse.observeForever(inboundShipmentCloseResponseObserver)

            BDDMockito.given(repository.closeShipment(1)).willReturn(flow)

            viewModel.closeShipment(1)

            Assert.assertNotNull(viewModel.inboundShipmentCloseResponse.value)
        }
    }

    @Test
    fun `empty data when close shipment not successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.inboundShipmentCloseResponse.observeForever(inboundShipmentCloseResponseObserver)

            BDDMockito.given(repository.closeShipment(1)).willReturn(flow)

            viewModel.closeShipment(1)

            Assert.assertNull(viewModel.inboundShipmentCloseResponse.value?.data)
        }
    }
}