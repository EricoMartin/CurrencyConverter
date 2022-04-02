package com.basebox.ratexchange.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basebox.ratexchange.data.local.entity.RatesModel
import com.basebox.ratexchange.repos.MainRepository
import com.basebox.ratexchange.util.DispatcherProvider
import com.basebox.ratexchange.util.Resource
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.StrictMath.round
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.refreshDB()
        }
    }

    sealed class RateEvent {
        class Success(val result: String) : RateEvent()
        class Failure(val error: String) : RateEvent()
        object Loading : RateEvent()
        object Empty : RateEvent()
    }

    val array = JsonArray()
    private val _conversion = MutableStateFlow<RateEvent>(RateEvent.Empty)
    val conversion: StateFlow<RateEvent> = _conversion
    private val _baseCurrencyType: MutableLiveData<String> = MutableLiveData<String>()

    val baseCurrencyTyped: LiveData<String>
        get() = _baseCurrencyType

    private val _timeRateData = MutableStateFlow<RateEvent>(RateEvent.Empty)
    val timeRateData get() = _timeRateData

    fun convert(amount: String, baseCurrency: String, toCurrency: String) {
        val from = amount.toFloatOrNull()
        _baseCurrencyType.value =
            "$from $baseCurrency"
        if (from == null) {
            _conversion.value = RateEvent.Failure("Invalid exchange amount")
            return
        }

        viewModelScope.launch(dispatcher.io) {
            _conversion.value = RateEvent.Loading
            val remoteRate = repository.getRate(baseCurrency.lowercase())
            if (remoteRate is Resource.Success) {
                repository.insertRatesInDB(remoteRate.data!!)
                val rates = repository.getDBRates(baseCurrency.lowercase())
                val rate = getCurrencyRate(toCurrency, rates.rates)
                Log.d("ViewModelResponse", "Rate = $rate")
                if (rate == 0.0) {
                    _conversion.value = RateEvent.Failure("Error")
                } else {
                    val convertedRate = round(from * rate * 100) / 100
                    Log.d(
                        "HomeViewModel Response",
                        "Result = ${convertedRate.toDouble()} $toCurrency"
                    )
                    _conversion.value =
                        RateEvent.Success("$convertedRate $toCurrency")
                }
            } else if (remoteRate is Resource.Error) {
                _conversion.value =
                    RateEvent.Failure(remoteRate.message!!)
                Log.d("ViewModelErrResponse", "Rate = ${remoteRate.message}")
            }
        }
    }

    fun getHistoricalRates() {
        viewModelScope.launch(dispatcher.io) {
            when (val ratesResponse = repository.getTimelyRates()) {
                is Resource.Error -> {
                    _timeRateData.value =
                        RateEvent.Failure(ratesResponse.message!!)
                    Log.d("ViewModelErrResponse", "Rate = ${ratesResponse.message}")
                }
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates.uSD
                    _timeRateData.value = RateEvent.Success(ratesResponse.data.rates.uSD.toString())
                    Log.d("ViewModelResponse", "Rate = $rates")

                    for (i in rates.toString()) {
                        array.add(i)
                    }
                }
            }
        }
    }

    private fun getCurrencyRate(currency: String, rates: RatesModel): Double =
        when (currency) {
            "USD" -> rates.uSD
            "JPY" -> rates.jPY
            "BGN" -> rates.bGN
            "CZK" -> rates.cZK
            "DKK" -> rates.dKK
            "GBP" -> rates.gBP
            "HUF" -> rates.hUF
            "PLN" -> rates.pLN
            "RON" -> rates.rON
            "SEK" -> rates.sEK
            "CHF" -> rates.cHF
            "ISK" -> rates.iSK
            "NOK" -> rates.nOK
            "HRK" -> rates.hRK
            "TRY" -> rates.tRY
            "AUD" -> rates.aUD
            "BRL" -> rates.bRL
            "CAD" -> rates.cAD
            "CNY" -> rates.cNY
            "HKD" -> rates.hKD
            "IDR" -> rates.iDR
            "ILS" -> rates.iLS
            "INR" -> rates.iNR
            "KRW" -> rates.kRW
            "MXN" -> rates.mXN
            "MYR" -> rates.mYR
            "NZD" -> rates.nZD
            "PHP" -> rates.pHP
            "SGD" -> rates.sGD
            "THB" -> rates.tHB
            "ZAR" -> rates.zAR
            "EUR" -> rates.eUR

            else -> rates.eUR
        }
}