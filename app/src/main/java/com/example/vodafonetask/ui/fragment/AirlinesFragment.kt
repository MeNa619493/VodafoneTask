package com.example.vodafonetask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vodafonetask.AirlineApplication
import com.example.vodafonetask.adapters.AirlinesAdapter
import com.example.vodafonetask.databinding.FragmentAirlinesBinding
import com.example.vodafonetask.viewmodels.AirlineApiStatus
import com.example.vodafonetask.viewmodels.AirlinesViewModel
import com.example.vodafonetask.viewmodels.AirlinesViewModelFactory

class AirlinesFragment : Fragment() {

    private var _binding: FragmentAirlinesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirlinesViewModel by viewModels {
        AirlinesViewModelFactory((requireActivity().application as AirlineApplication).repository)
    }

    private val mAdapter by lazy { AirlinesAdapter(AirlinesAdapter.AirlineClickListener { airline ->
        viewModel.openDetailFragment(airline)
    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirlinesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        observeCachingStatus()
        setupRecyclerView()
        observeAirlinesList()
        observeNavigationEvent()
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbarAirlineFragment)
    }

    private fun observeCachingStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                AirlineApiStatus.LOADING -> {
                    binding.statusProgressBar.visibility = View.VISIBLE
                }
                AirlineApiStatus.ERROR -> {
                    binding.statusProgressBar.visibility = View.GONE
                }
                AirlineApiStatus.SUCCESS -> {
                    binding.statusProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun observeAirlinesList() {
        viewModel.airlineList.observe(viewLifecycleOwner) { airlines ->
            airlines.let {
                if (it.isNotEmpty()){
                    binding.rvAirlinesList.visibility = View.VISIBLE

                    mAdapter.submitList(it)
                }
                else{
                    binding.rvAirlinesList.visibility = View.GONE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvAirlinesList.adapter = mAdapter
        binding.rvAirlinesList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeNavigationEvent() {
        viewModel.navigateToDetailFragmentEvent.observe(viewLifecycleOwner) { airline ->
            if (airline != null) {
                findNavController().navigate(AirlinesFragmentDirections.actionAirlinesFragmentToAirlineDetailsFragment(airline))
                viewModel.doneNavigating()
            } else {
                Log.d("AirlinesFragment", "observeNavigationEvent: airline is null")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}