package com.example.vodafonetask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AirlinesDetailsViewModel: ViewModel() {
    private val _navigateToWebViewFragmentEvent = MutableLiveData<Boolean>()
    val navigateToWebViewFragmentEvent: LiveData<Boolean>
        get() = _navigateToWebViewFragmentEvent

    fun openWebViewFragment() {
        _navigateToWebViewFragmentEvent.value = true
    }

    fun doneNavigating() {
        _navigateToWebViewFragmentEvent.value = false
    }
}