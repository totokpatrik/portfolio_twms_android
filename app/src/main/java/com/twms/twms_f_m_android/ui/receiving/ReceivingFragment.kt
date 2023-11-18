package com.twms.twms_f_m_android.ui.receiving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.databinding.FragmentReceivingBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReceivingFragment : Fragment() {
    private lateinit var binding: FragmentReceivingBinding
    private val viewModel: ReceivingViewModel by viewModels()

    private lateinit var navController: NavController

    @Inject
    lateinit var receivingAdapter: ReceivingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceivingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = receivingAdapter

        receivingAdapter.setOnItemClickListener {
            val bundle = bundleOf("inbound_shipment" to it)
            navController.navigate(R.id.action_receivingFragment_to_inboundShipmentFragment, bundle)
        }

        viewModel.getAllReceivingInboundShipment()

        viewModel.receivingResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    receivingAdapter.setInboundShipmentList(result.data?.data!!)
                    if (result.data.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.action_receivingFragment_to_menuFragment)
            }
        })
    }
}