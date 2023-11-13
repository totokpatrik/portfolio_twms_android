package com.twms.twms_f_m_android.ui.receive

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.twms.twms_f_m_android.MainCoroutinesRule
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Receive
import com.twms.twms_f_m_android.repository.ReceiveRepository
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
class ReceiveViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: ReceiveViewModel

    @Mock
    lateinit var receiveResponse: CustomResponse<String>

    @Mock
    lateinit var repository: ReceiveRepository

    @Mock
    lateinit var receiveResponseObserver: Observer<Resource<CustomResponse<String>>>

    @Mock
    private lateinit var receive: Receive

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ReceiveViewModel(repository)
    }

    @Test
    fun `return ok when receive successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(receiveResponse))
            }

            viewModel.receiveResponse.observeForever(receiveResponseObserver)

            BDDMockito.given(repository.receive(receive)).willReturn(flow)

            viewModel.receive(receive)

            Assert.assertNotNull(viewModel.receiveResponse.value)
        }
    }

    @Test
    fun `empty data when receive not successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.receiveResponse.observeForever(receiveResponseObserver)

            BDDMockito.given(repository.receive(receive)).willReturn(flow)

            viewModel.receive(receive)

            Assert.assertNull(viewModel.receiveResponse.value?.data)
        }
    }
}