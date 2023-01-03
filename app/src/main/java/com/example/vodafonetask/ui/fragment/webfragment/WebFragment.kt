package com.example.vodafonetask.ui.fragment.webfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.vodafonetask.databinding.FragmentWebBinding


class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebBinding.inflate(inflater, container, false)

        binding.airlineWebView.webViewClient = WebViewClient()
        val websiteUrl: String = WebFragmentArgs.fromBundle(requireArguments()).website
        Log.d("WebFragment", "https://$websiteUrl")
        binding.airlineWebView.loadUrl(setWebsiteUrlString(websiteUrl))

        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}