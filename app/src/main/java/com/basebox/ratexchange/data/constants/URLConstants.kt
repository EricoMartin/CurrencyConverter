package com.basebox.ratexchange.data.constants

class URLConstants {
    companion object {
        const val BASE_URL =
            "https://api.exchangerate.host/"
        const val USD_URL = "latest?source=ecb"
        const val TIME_URL =
            "timeseries?start_date=2022-01-01&end_date=2022-03-04&symbols=usd&source=ecb"
    }
}