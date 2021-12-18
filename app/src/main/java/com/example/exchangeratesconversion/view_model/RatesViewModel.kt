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
import kotlin.math.round

@HiltViewModel
class RatesViewModel @Inject constructor(
    val useCase: RatesUseCase
) : ViewModel() {

    // ViewState
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState.LoadingState)
    val viewState: State<ViewState> = _viewState


    val amount: MutableState<String> = mutableStateOf("10")
    val fromCurrency: MutableState<String> = mutableStateOf("EUR")
    val toCurrency: MutableState<String> = mutableStateOf("USD")
    val conversion: MutableState<String> = mutableStateOf("")

    init {
        viewModelScope.launch {
            _viewState.value = ViewState.LoadingState
            try {
                _viewState.value = ViewState.SuccessState(useCase("EUR"))
                convert(fromCurrency.value, toCurrency.value, amount.value)
            } catch (e: Exception) {
                _viewState.value = ViewState.FailedState("Error")
            }
        }
    }

    fun convert(
        from: String,
        to: String,
        amount: String,
    ) {
        viewModelScope.launch {
            try {
                val queryFrom = useCase(from)
                val requestedRate = getRequestedRate(to, queryFrom.rates!!)
                val amountEntered = amount.toFloat()
                conversion.value = "${(amountEntered * requestedRate!!).round(4)}"
            } catch (e: Exception) {
                _viewState.value = ViewState.FailedState("Error")
            }
        }
    }

    private fun getRequestedRate(currency: String, rate: RatesModel) = when (currency) {
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

    fun listOfCurrencies(): List<String> {
        return listOf(
            "AUD",
            "CAD",
            "CHF",
            "EUR",
            "GBP",
            "JPY",
            "NZD",
            "RUB",
            "USD",
        )
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }


}