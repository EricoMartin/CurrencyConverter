package com.basebox.ratexchange.data.remote.service

import com.basebox.ratexchange.data.constants.URLConstants
import com.basebox.ratexchange.data.remote.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RatesAPI {
    @GET("${URLConstants.USD_URL}/{currency}.json")
    suspend fun getRates(@Path("currency") currency: String): Response<RateResponse>
}