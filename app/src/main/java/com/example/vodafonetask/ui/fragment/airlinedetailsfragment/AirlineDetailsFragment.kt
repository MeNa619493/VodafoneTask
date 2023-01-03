package com.example.vodafonetask.ui.fragment.airlinedetailsfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vodafonetask.databinding.FragmentAirlineDetailsBinding
import com.example.vodafonetask.models.Airline


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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        airline = AirlineDetailsFragmentArgs.fromBundle(requireArguments()).airline
        setupCardView()
        setupBackButton()
        setupVisitButton()
        observeNavigationEvent()
        observeShowToastEvent()
    }

    private fun setupActionBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarAirlineDetailFragment)
    }

    private fun setupCardView() {
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
            airline.website?.let {
                if (viewModel.isWebsiteLinkValid(it)) {
                    viewModel.openWebViewFragment()

                    /*
                    //make implicit intent to browser
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(setWebsiteUrlString(it)))
                    val browserChooserIntent =
                        Intent.createChooser(browserIntent, "Choose browser")
                    startActivity(browserChooserIntent)
                     */

                } else {
                    viewModel.showURLNotValidToast()
                }
            }
        }
    }

    /*
    private fun setWebsiteUrlString(websiteString: String): String {
        var websiteUrl = websiteString
        if (!websiteUrl.startsWith("www")) {
            websiteUrl = "www.$websiteUrl"
        }
        if (!websiteUrl.startsWith("http")) {
            websiteUrl = "https://$websiteUrl"
        }
        return websiteUrl
    }
     */

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

    private fun observeShowToastEvent() {
        viewModel.showToastEvent.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireActivity(),"The website link is not valid!", Toast.LENGTH_SHORT).show()
                viewModel.doneShowingURLNotValidToast()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}