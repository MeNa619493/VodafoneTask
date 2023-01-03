package com.example.vodafonetask.ui.fragment.airlinesfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.vodafonetask.databinding.FragmentAirlinesBinding

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
        binding.etSearch.addTextChangedListener(textWatcher)
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
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarAirlineFragment)
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

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            Log.d("onTextChanged", binding.etSearch.text.toString())
            if (binding.etSearch.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                viewModel.search(binding.etSearch.text.toString()).observe(viewLifecycleOwner) {
                    mAdapter.submitList(it)
                }
            } else {
                binding.rvAirlinesList.layoutManager?.scrollToPosition(0)
                mAdapter.submitList(viewModel.airlineList.value)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}