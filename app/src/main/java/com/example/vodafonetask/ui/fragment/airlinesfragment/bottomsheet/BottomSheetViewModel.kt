package com.example.vodafonetask.ui.fragment.airlinesfragment.bottomsheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.models.AirLineEntity
import com.example.vodafonetask.models.AirLineModel
import com.example.vodafonetask.util.Validation
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import retrofit2.Response

class BottomSheetViewModel(private val repository: Repository) : ViewModel() {
    private val _navigateToAirlinesFragmentEvent = MutableLiveData<Boolean>()
    val navigateToAirlinesFragmentEvent: LiveData<Boolean>
        get() = _navigateToAirlinesFragmentEvent

    private val _validationLiveEvent = LiveEvent<Validation>()
    val validationLiveEvent: LiveData<Validation>
        get() = _validationLiveEvent

    fun openAirlinesFragment() {
        _navigateToAirlinesFragmentEvent.value = true
    }

    fun doneNavigating() {
        _navigateToAirlinesFragmentEvent.value = false
    }

    private fun isValid(airline: AirLineModel) : Boolean {
        if (airline.name?.trim { it <= ' ' }?.isEmpty() == true) {
            _validationLiveEvent.value = Validation.NameEmpty
            return false
        }

        if (airline.slogan?.trim { it <= ' ' }?.isEmpty() == true) {
            _validationLiveEvent.value = Validation.SloganEmpty
            return false
        }

        if (airline.country?.trim { it <= ' ' }?.isEmpty() == true) {
            _validationLiveEvent.value = Validation.CountryEmpty
            return false
        }

        if (airline.headQuaters?.trim { it <= ' ' }?.isEmpty() == true) {
            _validationLiveEvent.value = Validation.HeadquatersEmpty
            return false
        }

        if (airline.established?.trim { it <= ' ' }?.isEmpty() == true) {
            _validationLiveEvent.value = Validation.EstablishedEmpty
            return false
        }

        return true
    }

    fun postAirline(airLine: AirLineModel) {
        viewModelScope.launch {
            if (isValid(airLine)) {
                val response = repository.postAirline(airLine)
                handlePostResponse(response)
            }
        }
    }

    private fun handlePostResponse(response: Response<AirLineModel>) {
        if (response.isSuccessful) {
            Log.d("post request",  response.body().toString())
            //openAirlinesFragment()
        } else {
            Log.d("post request",  response.message())
        }
    }
}