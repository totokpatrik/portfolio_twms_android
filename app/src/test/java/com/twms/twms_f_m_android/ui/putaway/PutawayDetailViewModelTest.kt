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
class PutawayDetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: PutawayDetailViewModel

    @Mock
    lateinit var putawayRejectResponse: CustomResponse<Putaway>

    @Mock
    lateinit var putawayResponse: CustomResponse<String>

    @Mock
    lateinit var repository: PutawayRepository

    @Mock
    lateinit var putawayRejectResponseObserver: Observer<Resource<CustomResponse<Putaway>>>

    @Mock
    lateinit var putawayResponseObserver: Observer<Resource<CustomResponse<String>>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = PutawayDetailViewModel(repository)
    }

    @Test
    fun `return reject successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(putawayRejectResponse))
            }

            viewModel.putawayRejectResponse.observeForever(putawayRejectResponseObserver)

            BDDMockito.given(repository.reject(1)).willReturn(flow)

            viewModel.reject(1)

            Assert.assertNotNull(viewModel.putawayRejectResponse.value)
        }
    }

    @Test
    fun `empty data when reject`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.putawayRejectResponse.observeForever(putawayRejectResponseObserver)

            BDDMockito.given(repository.reject(1)).willReturn(flow)

            viewModel.reject(1)

            Assert.assertNull(viewModel.putawayResponse.value?.data)
        }
    }

    @Test
    fun `return putaway successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(putawayResponse))
            }

            viewModel.putawayResponse.observeForever(putawayResponseObserver)

            BDDMockito.given(repository.putaway(1)).willReturn(flow)

            viewModel.putaway(1)

            Assert.assertNotNull(viewModel.putawayResponse.value)
        }
    }

    @Test
    fun `empty data when putaway`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.putawayResponse.observeForever(putawayResponseObserver)

            BDDMockito.given(repository.putaway(1)).willReturn(flow)

            viewModel.putaway(1)

            Assert.assertNotNull(viewModel.putawayResponse.value)
        }
    }
}