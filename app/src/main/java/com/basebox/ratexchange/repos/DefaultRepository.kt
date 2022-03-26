package com.basebox.ratexchange.repos


import com.basebox.ratexchange.data.remote.ApiCall.RemoteAPICall
import com.basebox.ratexchange.data.remote.RateResponse
import com.basebox.ratexchange.util.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteAPICall: RemoteAPICall
) : MainRepository {
    override suspend fun getRate(baseCurrency: String): Resource<RateResponse> {
        return try {
            val response = remoteAPICall.getRate(baseCurrency)
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
}

