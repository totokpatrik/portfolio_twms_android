package com.twms.twms_f_m_android.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.data.model.Auth
import com.twms.twms_f_m_android.databinding.FragmentLoginBinding
import com.twms.twms_f_m_android.util.Status
import com.twms.twms_f_m_android.util.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null)
                navController.navigate(R.id.action_loginFragment_to_menuFragment)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    tokenViewModel.saveToken(result.data!!.data!!.tokenString)
                }
                Status.ERROR -> {
                    binding.txtError.text = result.message
                }
            }
        }

        binding.btnLogin.setOnClickListener{
            viewModel.login(
                Auth(binding.txtUserId.text.toString(), binding.txtPassword.text.toString()))
        }
    }

}