package com.basebox.ratexchange.data.remote

import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("date") val date: String,
    @SerializedName("usd") val usd: Usd
)
