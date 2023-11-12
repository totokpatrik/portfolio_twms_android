package com.twms.twms_f_m_android.ui.putaway

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.twms.twms_f_m_android.MainCoroutinesRule
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.repository.PutawayRepository
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
class PutawayViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: PutawayViewModel

    @Mock
    lateinit var putawayResponse: CustomResponse<List<Putaway>>

    @Mock
    lateinit var putawayAcknowledge: CustomResponse<Putaway>

    @Mock
    lateinit var repository: PutawayRepository

    @Mock
    lateinit var putawayResponseObserver: Observer<Resource<CustomResponse<List<Putaway>>>>

    @Mock
    lateinit var putawayAcknowledgeObserver: Observer<Resource<CustomResponse<Putaway>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = PutawayViewModel(repository)
    }

    @Test
    fun `return get putaway successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(putawayResponse))
            }

            viewModel.putawayResponse.observeForever(putawayResponseObserver)

            BDDMockito.given(repository.getAllPutaway()).willReturn(flow)

            viewModel.getAllPutaway()

            Assert.assertNotNull(viewModel.putawayResponse.value)
        }
    }

    @Test
    fun `empty data when get putaway successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.putawayResponse.observeForever(putawayResponseObserver)

            BDDMockito.given(repository.getAllPutaway()).willReturn(flow)

            viewModel.getAllPutaway()

            Assert.assertNull(viewModel.putawayResponse.value?.data)
        }
    }

    @Test
    fun `return acknowledge putaway successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(putawayAcknowledge))
            }

            viewModel.putawayAcknowledge.observeForever(putawayAcknowledgeObserver)

            BDDMockito.given(repository.acknowledge(1)).willReturn(flow)

            viewModel.acknowledge(1)

            Assert.assertNotNull(viewModel.putawayAcknowledge.value)
        }
    }

    @Test
    fun `empty data when acknowledge putaway successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.putawayAcknowledge.observeForever(putawayAcknowledgeObserver)

            BDDMockito.given(repository.acknowledge(1)).willReturn(flow)

            viewModel.acknowledge(1)

            Assert.assertNotNull(viewModel.putawayAcknowledge.value)
        }
    }
}