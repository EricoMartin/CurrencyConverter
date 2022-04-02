package com.basebox.ratexchange.data.converters

import androidx.room.TypeConverter
import com.basebox.ratexchange.data.remote.Motd
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MotdConverter {

    @TypeConverter
    fun fromMotdMessageList(value: Motd): String {
        val gson = Gson()
        val type = object : TypeToken<Motd>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMotdMessageList(value: String): Motd {
        val gson = Gson()
        val type = object : TypeToken<Motd>() {}.type
        return gson.fromJson(value, type)
    }
}