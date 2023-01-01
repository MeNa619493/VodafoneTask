package com.example.vodafonetask.data.network

import com.example.vodafonetask.models.Airline
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface AirlinesApi {
    @GET("/v1/airlines")
    fun getAirlinesAsync(): Deferred<List<Airline>>

    companion object {
        val retrofitService: AirlinesApi by lazy {
            RetrofitClient.getInstance().create(AirlinesApi::class.java)
        }
    }
}