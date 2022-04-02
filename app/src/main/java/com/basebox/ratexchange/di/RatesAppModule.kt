package com.basebox.ratexchange.di

import android.content.Context
import com.basebox.ratexchange.data.constants.URLConstants
import com.basebox.ratexchange.data.local.dao.RatesDao
import com.basebox.ratexchange.data.local.database.RatesDB
import com.basebox.ratexchange.data.remote.ApiCall.RemoteAPICall
import com.basebox.ratexchange.data.remote.service.RatesAPI
import com.basebox.ratexchange.repos.DefaultRepository
import com.basebox.ratexchange.repos.MainRepository
import com.basebox.ratexchange.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatesAppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):
            Retrofit {
        return Retrofit.Builder().baseUrl(URLConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideRatesApi(retrofit: Retrofit): RatesAPI {
        return retrofit.create(RatesAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(api: RemoteAPICall, ratesDao: RatesDao):
            MainRepository = DefaultRepository(api, ratesDao)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context): RatesDB {
        return RatesDB.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideDao(ratesDB: RatesDB): RatesDao {
        return ratesDB.ratesDao
    }


}