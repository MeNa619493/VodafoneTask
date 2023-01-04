package com.example.vodafonetask.ui.fragment.airlinesfragment

import android.util.Log
import androidx.lifecycle.*
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.models.Airline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class AirlineApiStatus { LOADING, ERROR, SUCCESS }

class AirlinesViewModel(private val repository: Repository) : ViewModel() {
    private val _status = MutableLiveData<AirlineApiStatus>()
    val status: LiveData<AirlineApiStatus>
        get() = _status

    private var _airlineList = repository.getAllAirlines().asLiveData(Dispatchers.IO)
    val airlineList: LiveData<List<Airline>>
        get() = _airlineList

    private val _navigateToDetailFragmentEvent = MutableLiveData<Airline?>()
    val navigateToDetailFragmentEvent: LiveData<Airline?>
        get() = _navigateToDetailFragmentEvent

    init {
        viewModelScope.launch {
            try {
                repository.refreshAirlines()
            } catch (e: Exception) {
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

    fun search(text: String): LiveData<List<Airline>> {

         val searchString = text.trim { it <= ' ' }

        val searchList = repository.getAllAirlines().map {
            it.filter { item ->
                if (item.name != null) {
                    item.name.lowercase().contains(searchString.lowercase())
                } else {
                    false
                }
            }
        }.asLiveData(Dispatchers.IO)

        return searchList
    }

}