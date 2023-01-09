package com.example.vodafonetask.ui.fragment.airlinesfragment

import androidx.lifecycle.*
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.models.AirLineEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class AirlineApiStatus { LOADING, ERROR, SUCCESS }

class AirlinesViewModel(private val repository: Repository) : ViewModel() {
    private val _status = MutableLiveData<AirlineApiStatus>()
    val status: LiveData<AirlineApiStatus>
        get() = _status

    private var _airlineList = repository.getAllAirlines().asLiveData(Dispatchers.IO)
    val airlineList: LiveData<List<AirLineEntity>>
        get() = _airlineList

    private val _navigateToDetailFragmentEvent = MutableLiveData<AirLineEntity?>()
    val navigateToDetailFragmentEvent: LiveData<AirLineEntity?>
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

    fun openDetailFragment(airline: AirLineEntity) {
        _navigateToDetailFragmentEvent.value = airline
    }

    fun doneNavigating() {
        _navigateToDetailFragmentEvent.value = null
    }

    fun search(text: String): LiveData<List<AirLineEntity>> {

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