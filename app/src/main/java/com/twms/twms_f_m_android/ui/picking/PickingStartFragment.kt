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
import com.twms.twms_f_m_android.databinding.FragmentPickingStartBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickingStartFragment : Fragment() {
    private lateinit var binding: FragmentPickingStartBinding
    private val viewModel: PickingStartViewModel by viewModels()
    private val args: PickingStartFragmentArgs by navArgs()

    private lateinit var navController: NavController

    private var outboundShipmentId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickingStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        outboundShipmentId = args.outboundShipmentId

        binding.btnPick.setOnClickListener {
            viewModel.start(binding.otfPalletNumber.editText?.text.toString())
        }

        viewModel.startResponse.observe(viewLifecycleOwner)  {result ->
            when(result.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val bundle = bundleOf("outboundShipmentId" to outboundShipmentId, "inventory" to result.data!!.data!!)
                    navController.navigate(R.id.action_pickingStartFragment_to_pickingPickFragment, bundle)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}