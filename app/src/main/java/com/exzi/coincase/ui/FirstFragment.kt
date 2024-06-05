package com.exzi.coincase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.exzi.coincase.MainActivity
import com.exzi.coincase.R
import com.exzi.coincase.databinding.FragmentFirstBinding
import com.exzi.coincase.utils.Resource
import com.exzi.coincase.viewModel.SpotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var spotViewModel: SpotViewModel
    private var name: String? = null
    private var pairId: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupSeekBarListener()
        observeCoinResponse()
        setupClickListeners()
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function that listens to touch events on the SeekBar
     **/
    private fun setupSeekBarListener() {
        binding.customSeekBar.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
            }
            false
        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function that observes the coin data response
     **/
    private fun observeCoinResponse() {
        spotViewModel.response.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> resource.data?.body()?.let { response ->
                    response.data[0]?.let { result ->
                        name = result.name
                        pairId = result.main?.id
                        updateUI(result)
                    }
                    (requireActivity() as MainActivity).hideProgressBar()
                }
                is Resource.Error -> {
                    spotViewModel.clearResponse()
                    (requireActivity() as MainActivity).hideProgressBar()
                }
                is Resource.Loading -> { (requireActivity() as MainActivity).showProgressBar() }
            }
        })
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function that updates the UI with new data
     **/
    private fun updateUI(data: com.exzi.coincase.data.defaultTicker.Data) {
        binding.apply {
            sellPriceTxt.text = data.volumeF
            sellQuantityTxt.text = data.athF
            sellPriceTxtt.text = data.volume.toString()
            sellQuantityTxtt.text = data.athF
        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Tıklama işlemlerinin yapıldığı fonksiyon
     **/
    private fun setupClickListeners() {
        binding.mcvBuy.setOnClickListener {
            val bundle = Bundle().apply {
                putString("name", name)
                pairId?.let { putDouble("pairId", it) }
            }
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }
    }

    /**
     * @author Ahmet Alperen Dal
     * @explanation : Function that initializes the fragment
     **/
    private fun initialize() {
        (requireActivity() as MainActivity).showProgressBar()
        spotViewModel = ViewModelProvider(requireActivity())[SpotViewModel::class.java]
        spotViewModel.getSpot()
    }
}
