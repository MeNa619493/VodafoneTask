package com.example.vodafonetask.viewmodels

import android.provider.ContactsContract.CommonDataKinds.Website
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AirlinesDetailsViewModel: ViewModel() {
    private val _navigateToWebViewFragmentEvent = MutableLiveData<Boolean>()
    val navigateToWebViewFragmentEvent: LiveData<Boolean>
        get() = _navigateToWebViewFragmentEvent

    private val _showToastEvent = MutableLiveData<Boolean>()
    val showToastEvent: LiveData<Boolean>
        get() = _showToastEvent

    fun openWebViewFragment() {
        _navigateToWebViewFragmentEvent.value = true
    }

    fun doneNavigating() {
        _navigateToWebViewFragmentEvent.value = false
    }

    fun isWebsiteLinkValid(website: String): Boolean {
        val webUrl =
            "^((ftp|http|https):\\/\\/)?(www.)?(?!.*(ftp|http|https|www.))[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+((\\/)[\\w#]+)*(\\/\\w+\\?[a-zA-Z0-9_]+=\\w+(&[a-zA-Z0-9_]+=\\w+)*)?$"
        if (website.trim { it <= ' ' }.isNotEmpty()) {
            if (!website.matches(webUrl.toRegex())) {
                return false
            }
        }
        return true
    }

    fun showURLNotValidToast() {
        _showToastEvent.value = true
    }

    fun doneShowingURLNotValidToast() {
        _showToastEvent.value = false
    }
}