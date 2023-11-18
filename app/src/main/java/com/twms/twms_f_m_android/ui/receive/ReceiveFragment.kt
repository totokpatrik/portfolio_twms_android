package com.twms.twms_f_m_android.ui.receive

import android.os.Bundle
import android.text.SpannableStringBuilder
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
import com.twms.twms_f_m_android.data.model.Receive
import com.twms.twms_f_m_android.data.model.ReceiveLine
import com.twms.twms_f_m_android.databinding.FragmentReceiveBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiveFragment : Fragment() {
    private lateinit var binding: FragmentReceiveBinding
    private val viewModel: ReceiveViewModel by viewModels()
    private lateinit var navController: NavController
    private val args: ReceiveFragmentArgs by navArgs()

    private lateinit var receiveLine: ReceiveLine
    private lateinit var inboundShipment: InboundShipment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiveBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        receiveLine = args.inboundOrder
        inboundShipment = args.inboundShipment

        binding.otfInboundOrder.editText?.text = SpannableStringBuilder(receiveLine.inboundOrderName)
        binding.otfItem.editText?.text = SpannableStringBuilder(receiveLine.item.name)

        binding.btnReceive.setOnClickListener{

            val txtPalletNumber: String? =
                binding.otfPalletNumber.editText?.text.toString().ifBlank {
                    null
                }

            binding.otfQuantity.editText?.text.toString().ifBlank {
                binding.otfQuantity.editText?.error = "should not be blank"

                return@setOnClickListener
            }

            val receive = Receive(
                receiveLine.inboundOrderLineId,
                txtPalletNumber,
                receiveLine.item.id,
                binding.otfQuantity.editText?.text.toString().toInt()
            )

            viewModel.receive(receive)
        }

        viewModel.receiveResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    navigateToInboundShipment(inboundShipment)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToInboundShipment(inboundShipment)
            }
        })
    }

    fun navigateToInboundShipment(inboundShipment: InboundShipment) {
        val bundle = bundleOf("inbound_shipment" to inboundShipment)
        navController.navigate(R.id.action_receiveFragment_to_inboundShipmentFragment, bundle)
    }

}