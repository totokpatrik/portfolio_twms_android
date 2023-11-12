package com.twms.twms_f_m_android.ui.putaway

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
import com.twms.twms_f_m_android.databinding.FragmentPutawayBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class PutawayFragment : Fragment() {
    private lateinit var binding: FragmentPutawayBinding
    private val viewModel: PutawayViewModel by viewModels()
    private lateinit var navController: NavController

    @Inject
    lateinit var putawayAdapter: PutawayAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPutawayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.recycleView.adapter = putawayAdapter

        viewModel.getAllPutaway()

        putawayAdapter.setOnItemClickListener {
            viewModel.acknowledge(it.id.toInt())
        }

        viewModel.putawayResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {
                    putawayAdapter.setPutawayList(emptyList())
                    binding.ldgLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    putawayAdapter.setPutawayList(result.data?.data!!)
                    binding.ldgLoading.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.putawayAcknowledge.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {
                    putawayAdapter.setPutawayList(emptyList())
                    binding.ldgLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val bundle = bundleOf("putaway" to result.data?.data)
                    navController.navigate(R.id.action_putawayFragment_to_putawayDetailFragment, bundle)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.action_putawayFragment_to_menuFragment)
            }
        })
    }
}