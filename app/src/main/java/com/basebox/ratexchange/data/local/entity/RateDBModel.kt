package com.basebox.ratexchange.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverters
import com.basebox.ratexchange.data.converters.RatesConverter

data class RateDBModel(
    @Embedded val rateModelResponse: RateModelResponse,

    @Relation(parentColumn = "base", entityColumn = "EUR")
    @TypeConverters(RatesConverter::class)
    val ratesModel: RatesModel,

    )
