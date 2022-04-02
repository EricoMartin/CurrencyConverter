package com.basebox.ratexchange.data.local.dao

import androidx.room.*
import com.basebox.ratexchange.data.remote.RateResponse

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRate(rate: RateResponse)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRate(rate: RateResponse)

    @Query("SELECT * from rates_table WHERE base Like :rateString Limit 1")
    fun getRate(rateString: String): RateResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRates(vararg allRates: RateResponse)

    @Query("Delete from rates_table")
    fun deleteAllRates()
}