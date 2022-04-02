package com.basebox.ratexchange.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.basebox.ratexchange.data.converters.MotdConverter
import com.basebox.ratexchange.data.converters.RatesConverter
import com.basebox.ratexchange.data.remote.RateResponse

@Entity(tableName = "rates_table")
data class RateModelResponse(
    @PrimaryKey
    val base: String,

    val date: String,
    @Embedded
    @field:TypeConverters(MotdConverter::class)
    val motd: Motd,
    @field:TypeConverters(RatesConverter::class)
    val ratesModel: RatesModel,
    val success: Boolean
) {
    fun List<RateModelResponse>.asDomainModel(): List<RateResponse> {
        return map {
            RateResponse(
                base = it.base,
                date = it.date,
                motd = it.motd,
                rates = it.ratesModel,
                success = it.success
            )
        }
    }
}