package com.twms.twms_f_m_android.ui.picking

import android.os.Bundle
import android.text.SpannableStringBuilder
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
import com.twms.twms_f_m_android.databinding.FragmentPickingPickBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickingPickFragment : Fragment() {
    private lateinit var binding: FragmentPickingPickBinding
    private val viewModel: PickingPickViewModel by viewModels()
    private val args: PickingPickFragmentArgs by navArgs()

    private lateinit var navController: NavController

    private var pickId: Number = 0
    private lateinit var sourceLocationName: String
    private lateinit var quantity: Number

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickingPickBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel.getNextPick(args.outboundShipmentId)

        viewModel.nextPickResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    if (result.data?.data?.quantity?.toInt() == 0) {
                        val bundle = bundleOf(
                            "outboundShipmentId" to args.outboundShipmentId,
                            "inventory" to args.inventory
                        )
                        navController.navigate(R.id.action_pickingPickFragment_to_pickingFinishFragment, bundle)
                    } else {
                        pickId = result.data?.data?.id!!
                        sourceLocationName = result.data.data.sourceLocation.name
                        quantity = result.data.data.quantity
                        binding.otfSourceLocation.hint =
                            SpannableStringBuilder(sourceLocationName)
                        binding.otfQuantity.hint =
                            SpannableStringBuilder(quantity.toString())
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnPick.setOnClickListener {
            if (binding.otfSourceLocation.editText?.text.toString() != sourceLocationName) {
                Toast.makeText(activity, "Source location is not correct", Toast.LENGTH_SHORT).show()
            }
            if (binding.otfQuantity.editText?.text.toString().toInt() > quantity.toInt()) {
                Toast.makeText(activity, "Quantity is not correct", Toast.LENGTH_SHORT).show()
            }

            viewModel.pick(
                pickId,
                args.inventory.id,
                binding.otfQuantity.editText?.text.toString().toInt()
            )
        }

        binding.btnComplete.setOnClickListener {
            val bundle = bundleOf(
                "outboundShipmentId" to args.outboundShipmentId,
                "inventory" to args.inventory
            )
            navController.navigate(R.id.action_pickingPickFragment_to_pickingFinishFragment, bundle)
        }

        viewModel.pickResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {
                    
                }
                Status.SUCCESS -> {
                    Toast.makeText(activity, "Picked successfully.", Toast.LENGTH_SHORT).show()
                    val bundle = bundleOf(
                        "outboundShipmentId" to args.outboundShipmentId,
                        "inventory" to args.inventory
                    )
                    navController.navigate(R.id.pickingPickFragment, bundle)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}