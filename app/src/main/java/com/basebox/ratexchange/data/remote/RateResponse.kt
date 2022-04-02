package com.basebox.ratexchange.data.remote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.basebox.ratexchange.data.converters.RatesConverter
import com.basebox.ratexchange.data.local.entity.Motd
import com.basebox.ratexchange.data.local.entity.RateModelResponse
import com.basebox.ratexchange.data.local.entity.RatesModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rates_table")
data class RateResponse(
    @PrimaryKey
    @SerializedName("base")
    @Expose
    val base: String,

    @SerializedName("date")
    @Expose
    val date: String,

    @Embedded
    @SerializedName("motd")
    @Expose
    val motd: Motd,

    @SerializedName("rates")
    @Expose
    @field:TypeConverters(RatesConverter::class)
    val rates: RatesModel,

    @SerializedName("success")
    @Expose
    val success: Boolean
) {
    fun List<RateResponse>.asDatabaseModel(): List<RateModelResponse> {
        return map {
            RateModelResponse(
                base = it.base,
                date = it.base,
                motd = it.motd,
                ratesModel = it.rates,
                success = it.success
            )
        }
    }
}