package com.example.exchangeratesconversion.view_model

import com.example.exchangeratesconversion.network_components.model.RatesResponseModel

sealed class ViewState {
    object LoadingState : ViewState()
    data class SuccessState(val data: RatesResponseModel) : ViewState()
    data class FailedState(val errorMessage: String) : ViewState()
}