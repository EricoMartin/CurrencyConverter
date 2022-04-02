package com.basebox.ratexchange.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Motd(
    @ColumnInfo(name = "msg")
    val msg: String,

    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String
)