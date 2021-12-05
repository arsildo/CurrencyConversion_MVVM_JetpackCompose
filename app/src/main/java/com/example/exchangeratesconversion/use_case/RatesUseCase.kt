package com.example.exchangeratesconversion.use_case

import com.example.exchangeratesconversion.network_components.model.RatesResponseModel
import com.example.exchangeratesconversion.repository.ImplementedRatesRepository
import javax.inject.Inject

interface UseCaseRetriever {
    suspend operator fun invoke(base: String): RatesResponseModel
}

class RatesUseCase @Inject constructor(
    private val repository: ImplementedRatesRepository
) : UseCaseRetriever {
    override suspend fun invoke(base: String): RatesResponseModel {
        return repository.getAllRates(base)
    }
}