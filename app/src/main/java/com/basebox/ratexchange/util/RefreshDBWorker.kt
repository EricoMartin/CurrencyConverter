package com.basebox.ratexchange.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.basebox.ratexchange.data.local.database.RatesDB
import com.basebox.ratexchange.data.remote.ApiCall.RemoteAPICall
import com.basebox.ratexchange.repos.DefaultRepository

class RefreshDBWorker(
    context: Context,
    params: WorkerParameters,
    private val remoteAPICall: RemoteAPICall
) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val database = RatesDB.getInstance(applicationContext)
        val defaultRepository = DefaultRepository(remoteAPICall, database.ratesDao)
        return try {
            defaultRepository.refreshDB()
            Result.success()
        } catch (error: CausableError) {
            Result.failure()
        }
    }
}