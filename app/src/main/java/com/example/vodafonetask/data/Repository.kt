package com.example.vodafonetask.data

import android.util.Log
import com.example.vodafonetask.data.database.AirlinesDao
import com.example.vodafonetask.data.network.AirlinesApi
import com.example.vodafonetask.models.AirLineEntity
import com.example.vodafonetask.models.AirLineModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val retrofitService: AirlinesApi,
    private val airlinesDao: AirlinesDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun refreshAirlines() {
        withContext(defaultDispatcher) {
            val deferredAirlines = retrofitService.getAirlinesAsync().await()
            Log.d("Repository", "refreshAirlines:$deferredAirlines ")
            airlinesDao.insertAllAirlines(AirLineEntity.toEntityList(deferredAirlines))
        }
    }

    fun getAllAirlines() = airlinesDao.getAllAirlines()

    suspend fun postAirline(airline: AirLineModel) = retrofitService.postAirline(airline)
}