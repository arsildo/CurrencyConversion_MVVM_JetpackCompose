package com.example.exchangeratesconversion.repository

import com.example.exchangeratesconversion.network_components.NetworkService
import com.example.exchangeratesconversion.network_components.model.RatesResponseModel
import javax.inject.Inject

interface ImplementedRatesRepository {
    suspend fun getAllRates(base: String): RatesResponseModel
}

class RatesRepository @Inject constructor(
    private val service: NetworkService
) : ImplementedRatesRepository {
    override suspend fun getAllRates(base: String): RatesResponseModel {
        return service.getExchangeRates(base)
    }
}