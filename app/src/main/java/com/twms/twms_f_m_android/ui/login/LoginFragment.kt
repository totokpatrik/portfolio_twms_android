package com.twms.twms_f_m_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner)  { token ->
            if (token != null)
                navController.navigate(R.id.action_loginFragment_to_menuFragment)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.SUCCESS -> {
                    tokenViewModel.saveToken(result.data?.data!!.tokenString)
                    binding.progressBar.isVisible = false
                }
                Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.txtError.text = result.data?.message
                }
            }
        }

        binding.btnLogin.setOnClickListener{
            viewModel.login(
                Auth(binding.txtUserId.text.toString(), binding.txtPassword.text.toString())
            )
        }
    }
}