package com.example.vodafonetask.ui.fragment.airlinesfragment.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.ui.fragment.airlinesfragment.AirlinesViewModel

class BottomSheetViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BottomSheetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}