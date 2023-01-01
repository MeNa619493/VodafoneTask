package com.example.vodafonetask.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.vodafonetask.R
import com.example.vodafonetask.databinding.FragmentAirlinesBinding
import com.example.vodafonetask.databinding.FragmentWebBinding
import kotlin.math.log


class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebBinding.inflate(inflater, container, false)

        binding.airlineWebView.webViewClient = object : WebViewClient() {}
        binding.airlineWebView.settings.javaScriptEnabled = true
        val websiteUrl: String = WebFragmentArgs.fromBundle(requireArguments()).website
        Log.d("WebFragment", "https://$websiteUrl")
        
        binding.airlineWebView.loadUrl(websiteUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}