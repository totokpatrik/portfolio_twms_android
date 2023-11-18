package com.twms.twms_f_m_android.ui.loading

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
import com.twms.twms_f_m_android.data.model.Load
import com.twms.twms_f_m_android.databinding.FragmentLoadingDetailBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class LoadingDetailFragment : Fragment() {
    private lateinit var binding: FragmentLoadingDetailBinding
    private val viewModel: LoadingDetailViewModel by viewModels()
    private val args: LoadingDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController

    @Inject
    lateinit var loadingDetailAdapter: LoadingDetailAdapter

    private lateinit var loadId: Number
    private lateinit var load: Load
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = loadingDetailAdapter

        loadId = args.loadId

        viewModel.getSingle(loadId)

        viewModel.getResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    load = result.data?.data!!
                    loadingDetailAdapter.setInventoryList(load.outboundShipment.location.inventories!!)
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLoad.setOnClickListener {
            var inventoryId: UUID? = null
            load.outboundShipment.location.inventories!!.forEach {
                if (it.palletNumber == binding.otfPalletNumber.editText?.text.toString() && !it.isLoaded) {
                    inventoryId = it.id
                }
            }

            if (inventoryId == null) {
                Toast.makeText(activity, "Not valid pallet number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.load(inventoryId!!)
        }

        viewModel.loadResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    val bundle = bundleOf("loadId" to loadId)
                    navController.navigate(R.id.loadingDetailFragment, bundle)
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnComplete.setOnClickListener {
            load.outboundShipment.location.inventories!!.forEach {
                if (!it.isLoaded) {
                    Toast.makeText(activity, "Not every pallet is loaded", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            viewModel.complete(loadId)
        }

        viewModel.loadCompleteResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    navController.navigate(R.id.action_loadingDetailFragment_to_menuFragment)
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}