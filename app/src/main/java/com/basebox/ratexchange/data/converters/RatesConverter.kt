package com.basebox.ratexchange.data.converters

import androidx.room.TypeConverter
import com.basebox.ratexchange.data.local.entity.RatesModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatesConverter {
    @TypeConverter
    fun fromRatesList(value: RatesModel): String {
        val gson = Gson()
        return gson.toJson(value).toString()
    }

    @TypeConverter
    fun toRatesList(value: String): RatesModel {
        val gson = Gson()
        val type = object : TypeToken<RatesModel>() {}.type
        return gson.fromJson(value, type)
    }
}