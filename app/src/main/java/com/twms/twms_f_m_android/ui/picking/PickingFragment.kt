package com.twms.twms_f_m_android.ui.picking

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
import com.twms.twms_f_m_android.databinding.FragmentPickingBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PickingFragment : Fragment() {
    private lateinit var binding: FragmentPickingBinding
    private val viewModel: PickingViewModel by viewModels()

    private lateinit var navController: NavController

    @Inject
    lateinit var pickingAdapter: PickingAdapter

    private var outboundShipmentId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = pickingAdapter

        pickingAdapter.setOnItemClickListener {
            outboundShipmentId = it.id.toInt()
            viewModel.acknowledge(outboundShipmentId)
        }

        viewModel.getPickingShipments()

        viewModel.outboundShipmentResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    pickingAdapter.setOutboundShipmentList(result.data?.data!!)
                    if (result.data.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.acknowledgeResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val bundle = bundleOf("outboundShipmentId" to outboundShipmentId)
                    navController.navigate(R.id.action_pickingFragment_to_pickingStartFragment, bundle)
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
                navController.navigate(R.id.action_pickingFragment_to_menuFragment)
            }
        })
    }
}