package com.example.vodafonetask.viewmodels

import androidx.lifecycle.*
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.models.Airline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class AirlineApiStatus { LOADING, ERROR, DONE }

class AirlinesViewModel(private val repository: Repository) : ViewModel() {
    private val _status = MutableLiveData<AirlineApiStatus>()
    val status: LiveData<AirlineApiStatus>
        get() = _status

    private var _airlineList = repository.getAllAirlines().asLiveData()
    val airlineList: LiveData<List<Airline>>
        get() = _airlineList

    private val _navigateToDetailFragmentEvent = MutableLiveData<Airline?>()
    val navigateToDetailFragmentEvent: LiveData<Airline?>
        get() = _navigateToDetailFragmentEvent

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(AirlineApiStatus.LOADING)
            try {
                repository.refreshAirlines()
                _status.postValue(AirlineApiStatus.DONE)
            } catch (e: Exception) {
                _status.postValue(AirlineApiStatus.ERROR)
                println("Exception getting data from airlines api: $e.message")
            }
        }
    }

    fun openDetailFragment(airline: Airline) {
        _navigateToDetailFragmentEvent.value = airline
    }

    fun doneNavigating() {
        _navigateToDetailFragmentEvent.value = null
    }

}