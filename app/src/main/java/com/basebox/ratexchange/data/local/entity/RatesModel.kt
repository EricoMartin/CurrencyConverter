package com.basebox.ratexchange.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class RatesModel(
    @SerializedName("AUD")
    @Expose
    val aUD: Double,
    @SerializedName("BGN")
    @Expose
    val bGN: Double,
    @SerializedName("BRL")
    @Expose
    val bRL: Double,
    @SerializedName("CAD")
    @Expose
    val cAD: Double,
    @SerializedName("CHF")
    @Expose
    val cHF: Double,
    @SerializedName("CNY")
    @Expose
    val cNY: Double,
    @SerializedName("CZK")
    @Expose
    val cZK: Double,
    @SerializedName("DKK")
    @Expose
    val dKK: Double,
    @SerializedName("EUR")
    @Expose
    @PrimaryKey
    val eUR: Double,
    @SerializedName("GBP")
    @Expose
    val gBP: Double,
    @SerializedName("HKD")
    @Expose
    val hKD: Double,
    @SerializedName("HRK")
    @Expose
    val hRK: Double,
    @SerializedName("HUF")
    @Expose
    val hUF: Double,
    @SerializedName("IDR")
    @Expose
    val iDR: Double,
    @SerializedName("ILS")
    @Expose
    val iLS: Double,
    @SerializedName("INR")
    @Expose
    val iNR: Double,
    @SerializedName("ISK")
    @Expose
    val iSK: Double,
    @SerializedName("JPY")
    @Expose
    val jPY: Double,
    @SerializedName("KRW")
    @Expose
    val kRW: Double,
    @SerializedName("MXN")
    @Expose
    val mXN: Double,
    @SerializedName("MYR")
    @Expose
    val mYR: Double,
    @SerializedName("NOK")
    @Expose
    val nOK: Double,
    @SerializedName("NZD")
    @Expose
    val nZD: Double,
    @SerializedName("PHP")
    @Expose
    val pHP: Double,
    @SerializedName("PLN")
    @Expose
    val pLN: Double,
    @SerializedName("RON")
    @Expose
    val rON: Double,
    @SerializedName("SEK")
    @Expose
    val sEK: Double,
    @SerializedName("SGD")
    @Expose
    val sGD: Double,
    @SerializedName("THB")
    @Expose
    val tHB: Double,
    @SerializedName("TRY")
    @Expose
    val tRY: Double,
    @SerializedName("USD")
    @Expose
    val uSD: Double,
    @SerializedName("ZAR")
    @Expose
    val zAR: Double
)
