package com.example.exchangeratesconversion.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratesconversion.use_case.RatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    val useCase: RatesUseCase
) : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState.LoadingState)
    val viewState: State<ViewState> = _viewState
    val lastTimeUpdated: MutableState<String> = mutableStateOf("")

    init {
        viewModelScope.launch {
            _viewState.value = ViewState.LoadingState
            try {
                val query = useCase("EUR")
                _viewState.value = ViewState.SuccessState(query)
                lastTimeUpdated.value = query.time_last_update_utc
            } catch (e: Exception) {
                _viewState.value = ViewState.FailedState("Error")
            }
        }
    }

}