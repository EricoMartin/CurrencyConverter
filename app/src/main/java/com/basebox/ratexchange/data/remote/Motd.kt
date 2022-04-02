package com.basebox.ratexchange.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Motd(
    @SerializedName("msg")
    @Expose
    val msg: String,
    @SerializedName("url")
    @Expose
    val url: String
)