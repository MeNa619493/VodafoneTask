package com.example.vodafonetask.ui.fragment.airlinesfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vodafonetask.data.Repository

class AirlinesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirlinesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AirlinesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}