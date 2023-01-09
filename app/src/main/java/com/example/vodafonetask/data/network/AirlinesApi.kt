package com.example.vodafonetask.data.network

import com.example.vodafonetask.models.AirLineModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AirlinesApi {
    @GET("/v1/airlines")
    fun getAirlinesAsync(): Deferred<List<AirLineModel>>

    @POST("/v1/airlines")
    suspend fun postAirline(@Body airline: AirLineModel): Response<AirLineModel>

    companion object {
        val retrofitService: AirlinesApi by lazy {
            RetrofitClient.getInstance().create(AirlinesApi::class.java)
        }
    }
}