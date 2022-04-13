package com.basebox.ratexchange

import android.app.Application
import androidx.work.*
import com.basebox.ratexchange.data.remote.RateResponse
import com.basebox.ratexchange.data.remote.service.RatesAPI
import com.basebox.ratexchange.util.RefreshDBWorker
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Response
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class RatesApplication : Application() {
    private var ratesAPI: RatesAPI = object : RatesAPI {
        override suspend fun getRates(currency: String): Response<RateResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getTimeRates(): Response<RateResponse> {
            TODO("Not yet implemented")
        }

        override suspend fun getRatesForDBAsync(currency: String): Response<RateResponse> {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate() {
        super.onCreate()
        setupWorkManagerJob()
    }

    private fun setupWorkManagerJob() {
        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.METERED).build()
        val work = PeriodicWorkRequestBuilder<RefreshDBWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RefreshDBWorker::class.java.name, ExistingPeriodicWorkPolicy.KEEP, work
        )
    }

}