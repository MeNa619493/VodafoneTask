package com.example.vodafonetask.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vodafonetask.AirlineApplication
import com.example.vodafonetask.R
import com.example.vodafonetask.databinding.FragmentAirlineDetailsBinding
import com.example.vodafonetask.databinding.FragmentAirlinesBinding
import com.example.vodafonetask.models.Airline
import com.example.vodafonetask.viewmodels.AirlinesDetailsViewModel
import com.example.vodafonetask.viewmodels.AirlinesViewModel
import com.example.vodafonetask.viewmodels.AirlinesViewModelFactory


class AirlineDetailsFragment : Fragment() {
    private var _binding: FragmentAirlineDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var airline: Airline
    private val viewModel: AirlinesDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirlineDetailsBinding.inflate(inflater, container, false)
        setupActionBar()
        airline = AirlineDetailsFragmentArgs.fromBundle(requireArguments()).airline
        setupCardView(airline)
        setupBackButton()
        setupVisitButton()
        observeNavigationEvent()
        return binding.root
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarAirlineDetailFragment)
    }

    private fun setupCardView(airline: Airline) {
        binding.tvAirlineTitle.text = airline.name
        binding.tvAirlineCountry.text = airline.country
        binding.tvAirlineSlogan.text = airline.slogan
        binding.tvHeadquartersDetails.text = airline.headQuaters
    }

    private fun setupBackButton() {
        binding.ivBackArrow.setOnClickListener {
            findNavController().navigate(AirlineDetailsFragmentDirections.actionAirlineDetailsFragmentToAirlinesFragment())
        }
    }

    private fun setupVisitButton() {
        binding.btnVisit.setOnClickListener {
            viewModel.openWebViewFragment()
        }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToWebViewFragmentEvent.observe(viewLifecycleOwner) { isNavigate ->
            if (isNavigate) {
                airline.website?.let {
                    findNavController().navigate(AirlineDetailsFragmentDirections.actionAirlineDetailsFragmentToWebFragment(it))
                    viewModel.doneNavigating()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}