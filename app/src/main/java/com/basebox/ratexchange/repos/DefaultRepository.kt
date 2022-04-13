package com.basebox.ratexchange.repos


import android.util.Log
import com.basebox.ratexchange.data.local.dao.RatesDao
import com.basebox.ratexchange.data.remote.ApiCall.RemoteAPICall
import com.basebox.ratexchange.data.remote.RateResponse
import com.basebox.ratexchange.util.CausableError
import com.basebox.ratexchange.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteAPICall: RemoteAPICall,
    private var ratesDao: RatesDao,
) : MainRepository {
    override suspend fun getRate(baseCurrency: String): Resource<RateResponse> {
        return try {
            val response = remoteAPICall.getRate(baseCurrency)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                ratesDao.insertRate(result)
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")

        }
    }

    override suspend fun getTimelyRates(): Resource<RateResponse> {
        return try {
            val response = remoteAPICall.getTimelyRates()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occurred")

        }
    }

    override suspend fun getDBRates(baseCurrency: String): RateResponse {
        val getRateFromDB = withContext(Dispatchers.IO) { ratesDao.getRate(baseCurrency) }
        Log.d("DefaultRepo", "$getRateFromDB")
        Resource.Success(getRateFromDB)
        return getRateFromDB
    }


    override suspend fun insertRatesInDB(rateResponse: RateResponse) {
        withContext(Dispatchers.IO) {
            ratesDao.insertRate(rateResponse)
        }
    }

    override suspend fun refreshDB() {
        withContext(Dispatchers.IO) {
            try {
                val arrayPlayList: ArrayList<RateResponse> =
                    arrayListOf(
                        remoteAPICall.getRatesForDB("eur").body()!!,
                        remoteAPICall.getRatesForDB("aud").body()!!,
                        remoteAPICall.getRatesForDB("bgn").body()!!,
                        remoteAPICall.getRatesForDB("brl").body()!!,
                        remoteAPICall.getRatesForDB("cad").body()!!,
                        remoteAPICall.getRatesForDB("chf").body()!!,
                        remoteAPICall.getRatesForDB("cny").body()!!,
                        remoteAPICall.getRatesForDB("czk").body()!!,
                        remoteAPICall.getRatesForDB("dkk").body()!!,
                        remoteAPICall.getRatesForDB("gbp").body()!!,
                        remoteAPICall.getRatesForDB("hkd").body()!!,
                        remoteAPICall.getRatesForDB("hrk").body()!!,
                        remoteAPICall.getRatesForDB("huf").body()!!,
                        remoteAPICall.getRatesForDB("idr").body()!!,
                        remoteAPICall.getRatesForDB("ils").body()!!,
                        remoteAPICall.getRatesForDB("inr").body()!!,
                        remoteAPICall.getRatesForDB("isk").body()!!,
                        remoteAPICall.getRatesForDB("jpy").body()!!,
                        remoteAPICall.getRatesForDB("krw").body()!!,
                        remoteAPICall.getRatesForDB("mxn").body()!!,
                        remoteAPICall.getRatesForDB("nok").body()!!,
                        remoteAPICall.getRatesForDB("myr").body()!!,
                        remoteAPICall.getRatesForDB("nzd").body()!!,
                        remoteAPICall.getRatesForDB("php").body()!!,
                        remoteAPICall.getRatesForDB("pln").body()!!,
                        remoteAPICall.getRatesForDB("ron").body()!!,
                        remoteAPICall.getRatesForDB("sek").body()!!,
                        remoteAPICall.getRatesForDB("sgd").body()!!,
                        remoteAPICall.getRatesForDB("thb").body()!!,
                        remoteAPICall.getRatesForDB("try").body()!!,
                        remoteAPICall.getRatesForDB("usd").body()!!,
                        remoteAPICall.getRatesForDB("zar").body()!!
                    )

                for (i in arrayPlayList.indices) {
                    ratesDao.insertRate(arrayPlayList[i])
                }
            } catch (cause: Throwable) {
                throw CausableError("Network Error", cause)
            }
        }
    }
}

