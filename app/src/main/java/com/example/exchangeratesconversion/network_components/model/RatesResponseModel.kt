package com.example.exchangeratesconversion.network_components.model

import com.google.gson.annotations.SerializedName

data class RatesResponseModel(
    val base_code: String,
    val documentation: String,
    val provider: String,
    @SerializedName("rates")
    val rates: RatesModel?= null,
    val result: String,
    val terms_of_use: String,
    val time_eol_unix: Int,
    val time_last_update_unix: Int,
    val time_last_update_utc: String,
    val time_next_update_unix: Int,
    val time_next_update_utc: String
)