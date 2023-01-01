package com.example.vodafonetask

import android.app.Application
import com.example.vodafonetask.data.Repository
import com.example.vodafonetask.data.database.AirlinesDatabase
import com.example.vodafonetask.data.network.AirlinesApi

class AirlineApplication: Application() {
    private val apiService by lazy { AirlinesApi.retrofitService }
    private val database by lazy { AirlinesDatabase.getInstance(this) }

    val repository by lazy { Repository(apiService, database.airlineDao) }
}