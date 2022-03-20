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


//        flow<Resource<RateResponse>> {
//            emit(safeApiCall { remoteAPICall.getRate(base) })
//        }.flowOn(Dispatchers.IO) as Resource<RateResponse>
//    }

//    override suspend fun getRate(baseCurrency: String): Resource<RateResponse> {
//        return getRates(baseCurrency)
//    }
}

