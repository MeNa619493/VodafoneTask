package com.example.vodafonetask.data

import android.util.Log
import com.example.vodafonetask.data.database.AirlinesDao
import com.example.vodafonetask.data.network.AirlinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val retrofitService: AirlinesApi, private val airlinesDao: AirlinesDao) {

    suspend fun refreshAirlines() {
        withContext(Dispatchers.IO) {
            val deferredAirlines = retrofitService.getAirlinesAsync().await()
            Log.d("Repository", "refreshAirlines:$deferredAirlines ")
            airlinesDao.insertAllAirlines(deferredAirlines)
        }
    }

    fun getAllAirlines() = airlinesDao.getAllAirlines()
}