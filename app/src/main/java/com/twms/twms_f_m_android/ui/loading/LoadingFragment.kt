package com.twms.twms_f_m_android.ui.loading

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
import com.twms.twms_f_m_android.databinding.FragmentLoadingBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoadingFragment : Fragment() {
    private lateinit var binding: FragmentLoadingBinding
    private val viewModel: LoadingViewModel by viewModels()

    private lateinit var navController: NavController

    @Inject
    lateinit var loadingAdapter: LoadingAdapter

    private lateinit var loadId: Number
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = loadingAdapter

        loadingAdapter.setOnItemClickListener {
            loadId = it.id.toInt()
            viewModel.acknowledge(loadId)
        }

        viewModel.get()

        viewModel.getResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    loadingAdapter.setLoadList(result.data?.data!!)
                    if (result.data.data.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.acknowledgeResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    val bundle = bundleOf("loadId" to loadId)
                    navController.navigate(R.id.action_loadingFragment_to_loadingDetailFragment, bundle)
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
                navController.navigate(R.id.action_loadingFragment_to_menuFragment)
            }
        })
    }
}