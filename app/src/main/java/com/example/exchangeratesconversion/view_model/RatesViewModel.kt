package com.example.exchangeratesconversion.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesconversion.network_components.model.RatesModel
import com.example.exchangeratesconversion.use_case.RatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    val useCase: RatesUseCase
) : ViewModel() {

    // ViewState
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState.LoadingState)
    val viewState: State<ViewState> = _viewState

    // List of Rates
    private val _currentRates: MutableState<RatesModel?> = mutableStateOf(RatesModel())
    var currentRates: State<RatesModel?> = _currentRates

    var lastTimeUpdated: MutableState<String?> = mutableStateOf("")

    init {
        viewModelScope.launch {
            _viewState.value = ViewState.LoadingState
            try {
                val query = useCase("EUR")
                _viewState.value = ViewState.SuccessState(query)
                _currentRates.value = query.rates
                lastTimeUpdated.value = query.time_last_update_utc
                currentRates
            } catch (e: Exception) {
                _viewState.value = ViewState.FailedState("Error")
            }
        }
    }
    fun getRate(currency: String, rate: RatesModel) = when (currency) {
        "AUD" -> rate.AUD
        "CAD" -> rate.CAD
        "CHF" -> rate.CHF
        "EUR" -> rate.EUR
        "GBP" -> rate.GBP
        "JPY" -> rate.JPY
        "NZD" -> rate.NZD
        "RUB" -> rate.RUB
        "USD" -> rate.USD
        else -> null
    }


}