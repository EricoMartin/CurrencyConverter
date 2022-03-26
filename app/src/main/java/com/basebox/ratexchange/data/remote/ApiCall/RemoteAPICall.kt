package com.basebox.ratexchange.data.remote.ApiCall

import com.basebox.ratexchange.data.remote.service.RatesAPI
import javax.inject.Inject

class RemoteAPICall @Inject constructor(private val apiService: RatesAPI) {
    suspend fun getRate(base: String) =
        apiService.getRates(base)

    suspend fun getTimelyRates() = apiService.getTimeRates()
}