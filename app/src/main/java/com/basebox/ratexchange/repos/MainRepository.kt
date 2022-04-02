package com.basebox.ratexchange.repos

import com.basebox.ratexchange.data.remote.RateResponse
import com.basebox.ratexchange.util.Resource

interface MainRepository {
    suspend fun getRate(baseCurrency: String): Resource<RateResponse>
    suspend fun getTimelyRates(): Resource<RateResponse>
    suspend fun getDBRates(baseCurrency: String): RateResponse
    suspend fun insertRatesInDB(rateResponse: RateResponse)
    suspend fun refreshDB()
}

