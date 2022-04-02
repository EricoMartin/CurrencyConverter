package com.basebox.ratexchange.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.basebox.ratexchange.data.local.dao.RatesDao
import com.basebox.ratexchange.data.remote.RateResponse

@Database(entities = [RateResponse::class], version = 11, exportSchema = false)
abstract class RatesDB : RoomDatabase() {
    abstract val ratesDao: RatesDao

    companion object {
        @Volatile
        private var INSTANCE: RatesDB? = null

        fun getInstance(context: Context): RatesDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, RatesDB::class.java,
                        "rates_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}