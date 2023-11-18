package com.twms.twms_f_m_android.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnPutaway.setOnClickListener{
            navController.navigate(R.id.action_menuFragment_to_putawayFragment)
        }

        binding.btnReceiving.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_receivingFragment)
        }

        binding.btnPicking.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_pickingFragment)
        }

        binding.btnLoading.setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_loadingFragment)
        }
    }
}