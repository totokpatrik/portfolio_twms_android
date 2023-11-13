package com.twms.twms_f_m_android.ui.inbound_shipment

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
import androidx.navigation.fragment.navArgs
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.data.model.InboundShipment
import com.twms.twms_f_m_android.databinding.FragmentInboundShipmentBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InboundShipmentFragment : Fragment() {
    private lateinit var binding: FragmentInboundShipmentBinding
    private val viewModel: InboundShipmentViewModel by viewModels()
    private lateinit var navController: NavController
    private val args: InboundShipmentFragmentArgs by navArgs()

    private lateinit var inboundShipment: InboundShipment

    @Inject
    lateinit var inboundShipmentAdapter: InboundShipmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInboundShipmentBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = inboundShipmentAdapter

        inboundShipment = args.inboundShipment

        binding.tvInboundShipment.text = inboundShipment.name
        binding.tvLocation.text = inboundShipment.location!!.name


        inboundShipmentAdapter.setOnItemClickListener {
            val bundle = bundleOf("inbound_order" to it, "inbound_shipment" to inboundShipment)
            navController.navigate(
                R.id.action_inboundShipmentFragment_to_receiveFragment,
                bundle
            )
        }

        viewModel.getShipment(inboundShipment.id.toString())

        viewModel.inboundShipmentResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    inboundShipmentAdapter.setInboundShipmentList(result.data?.data!!)
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnCloseInbound.setOnClickListener {
            viewModel.closeShipment(inboundShipment.id.toString().toInt())
        }

        viewModel.inboundShipmentCloseResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    navController.navigate(R.id.action_inboundShipmentFragment_to_receivingFragment)
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_inboundShipmentFragment_to_receivingFragment)
                }
            })
    }
}