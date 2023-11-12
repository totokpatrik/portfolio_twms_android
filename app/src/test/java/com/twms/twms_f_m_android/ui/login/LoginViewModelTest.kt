package com.twms.twms_f_m_android.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.twms.twms_f_m_android.MainCoroutinesRule
import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.data.model.AuthResponse
import com.twms.twms_f_m_android.data.model.CustomResponse
import com.twms.twms_f_m_android.repository.AuthRepository
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
class LoginViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: LoginViewModel

    @Mock
    lateinit var loginResponse: CustomResponse<AuthResponse>

    @Mock
    lateinit var repository: AuthRepository

    @Mock
    lateinit var movieDetailsResponseObserver: Observer<Resource<CustomResponse<AuthResponse>>>

    @Mock
    private lateinit var auth: Auth

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `return ok when login successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(loginResponse))
            }

            viewModel.loginResponse.observeForever(movieDetailsResponseObserver)

            BDDMockito.given(repository.login(auth)).willReturn(flow)

            viewModel.login(auth)

            Assert.assertNotNull(viewModel.loginResponse.value)
        }
    }

    @Test
    fun `empty data when login not successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.error("there was an error", null))
            }

            viewModel.loginResponse.observeForever(movieDetailsResponseObserver)

            BDDMockito.given(repository.login(auth)).willReturn(flow)

            viewModel.login(auth)

            Assert.assertNull(viewModel.loginResponse.value?.data)
        }
    }
}