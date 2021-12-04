package com.example.exchangeratesconversion.network_components

import com.example.exchangeratesconversion.network_components.model.RatesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("euro")
    suspend fun getExchangeRates(@Query("base") base: String): RatesResponseModel
}