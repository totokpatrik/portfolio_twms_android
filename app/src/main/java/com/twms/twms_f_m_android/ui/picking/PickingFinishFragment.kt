package com.twms.twms_f_m_android.ui.picking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.data.model.Location
import com.twms.twms_f_m_android.databinding.FragmentPickingFinishBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickingFinishFragment : Fragment() {
    private lateinit var binding: FragmentPickingFinishBinding
    private val viewModel: PickingFinishViewModel by viewModels()
    private val args: PickingFinishFragmentArgs by navArgs()

    private lateinit var navController: NavController

    private lateinit var destinationLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickingFinishBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel.getShipmentById(args.outboundShipmentId)

        viewModel.shipmentResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    destinationLocation = result.data?.data?.location!!
                    binding.otfDestinationLocation.hint = destinationLocation.name
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnPick.setOnClickListener {
            viewModel.finishPick(args.inventory.id, destinationLocation.id, args.outboundShipmentId)
        }

        viewModel.finishPickResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if (result.data?.data?.quantity?.toInt() != 0) {

                        // There are still picks navigate to start
                        val bundle = bundleOf(
                            "outboundShipmentId" to args.outboundShipmentId
                        )
                        navController.navigate(R.id.action_pickingFinishFragment_to_pickingStartFragment, bundle)
                    } else {
                        // No more picks return to picking
                        navController.navigate(R.id.action_pickingFinishFragment_to_pickingFragment)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}