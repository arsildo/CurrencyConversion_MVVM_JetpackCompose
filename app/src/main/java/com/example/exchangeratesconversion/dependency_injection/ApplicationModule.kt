package com.example.exchangeratesconversion.dependency_injection

import com.example.exchangeratesconversion.network_components.NetworkService
import com.example.exchangeratesconversion.network_components.model.RatesResponseModel
import com.example.exchangeratesconversion.network_components.model.RatesModel
import com.example.exchangeratesconversion.repository.ImplementedRatesRepository
import com.example.exchangeratesconversion.repository.RatesRepository
import com.example.exchangeratesconversion.use_case.RatesUseCase
import com.example.exchangeratesconversion.use_case.UseCaseRetriever
import com.google.gson.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://open.er-api.com/v6/latest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface ApplicationModuleInterface {
        @Binds
        @Singleton
        fun provideRepository(repository: RatesRepository): ImplementedRatesRepository

        @Binds
        @Singleton
        fun provideUseCase(useCase: RatesUseCase): UseCaseRetriever
    }


}