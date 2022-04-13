package com.basebox.ratexchange.data.remote.service

import com.basebox.ratexchange.data.constants.URLConstants
import com.basebox.ratexchange.data.remote.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesAPI {
    @GET(URLConstants.USD_URL)
    suspend fun getRates(@Query("base") currency: String): Response<RateResponse>

    @GET(URLConstants.TIME_URL)
    suspend fun getTimeRates(): Response<RateResponse>

    @GET(URLConstants.USD_URL)
    suspend fun getRatesForDBAsync(@Query("base") currency: String): Response<RateResponse>

}