package com.twms.twms_f_m_android.ui.putaway

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.data.model.Putaway
import com.twms.twms_f_m_android.databinding.FragmentPutawayDetailBinding
import com.twms.twms_f_m_android.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PutawayDetailFragment : Fragment() {
    private lateinit var binding: FragmentPutawayDetailBinding
    private val viewModel: PutawayDetailViewModel by viewModels()
    private lateinit var navController: NavController
    private val args: PutawayDetailFragmentArgs by navArgs()

    private lateinit var putaway: Putaway

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPutawayDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        putaway = args.putaway

        binding.otfSourceLocation.editText?.text = SpannableStringBuilder(putaway.inventory.location?.name)
        binding.otfPalletNumber.editText?.text = SpannableStringBuilder(putaway.inventory.palletNumber)
        val destinationLocation = "Destination Location " + putaway.destinationLocation?.name
        binding.otfDestinationLocation.hint = destinationLocation

        binding.btnPutaway.setOnClickListener {
            if (binding.otfDestinationLocation.editText?.text.toString() == putaway.destinationLocation?.name) {
                viewModel.putaway(putaway.id.toInt())
            } else {
                Toast.makeText(activity, "The destination location is not matching" + putaway.destinationLocation?.name + "   " + binding.otfDestinationLocation.editText?.text,Toast.LENGTH_LONG).show()
            }
        }

        viewModel.putawayResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    navController.navigate(R.id.action_putawayDetailFragment_to_putawayFragment)
                }
                Status.ERROR -> {
                    Toast.makeText(activity, result.message,Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_putawayDetailFragment_to_putawayFragment)
                }
            }
        }

        viewModel.putawayRejectResponse.observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    navController.navigate(R.id.action_putawayDetailFragment_to_putawayFragment)
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
                viewModel.reject(putaway.id.toInt())
            }
        })
    }
}