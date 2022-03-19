package com.basebox.ratexchange.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basebox.ratexchange.data.remote.Usd
import com.basebox.ratexchange.repos.MainRepository
import com.basebox.ratexchange.util.DispatcherProvider
import com.basebox.ratexchange.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.StrictMath.round
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    sealed class RateEvent {
        class Success(val result: String) : RateEvent()
        class Failure(val error: String) : RateEvent()
        object Loading : RateEvent()
        object Empty : RateEvent()
    }

    private val _conversion = MutableStateFlow<RateEvent>(RateEvent.Empty)
    val conversion: StateFlow<RateEvent> = _conversion

    fun convert(amount: String, baseCurrency: String, toCurrency: String) {
        val from = amount.toFloatOrNull()
        if (from == null) {
            _conversion.value = RateEvent.Failure("Invalid exchange amount")
            return
        }

        viewModelScope.launch(dispatcher.io) {
            _conversion.value = RateEvent.Loading
            when (val ratesResponse = repository.getRate(from.toString())) {
                is Resource.Error<*> -> _conversion.value =
                    RateEvent.Failure(ratesResponse.message!!)
                is Resource.Success<*> -> {
                    val rates = ratesResponse.data!!.usd
                    val rate: Int = getCurrencyRate(toCurrency, rates) as Int
                    if (rate == 0) {
                        _conversion.value = RateEvent.Failure("Error")
                    } else {
                        val convertedRate = round(from * rate * 100) / 100
                        _conversion.value =
                            RateEvent.Success("$convertedRate              $toCurrency")
                    }
                }
            }
        }
    }

    private fun getCurrencyRate(currency: String, rates: Usd) =
        when (currency.lowercase(Locale.getDefault())) {
            "inch1" -> rates.inch1
            "ada" -> rates.ada
            "aed" -> rates.aed
            "afn" -> rates.afn
            "algo" -> rates.algo
            "all" -> rates.all
            "amd" -> rates.amd
            "ang" -> rates.ang
            "aoa" -> rates.aoa
            "ars" -> rates.ars
            "atom" -> rates.atom
            "aud" -> rates.aud
            "avax" -> rates.avax
            "awg" -> rates.awg
            "azn" -> rates.azn
            "bam" -> rates.bam
            "bbd" -> rates.bbd
            "bch" -> rates.bch
            "bdt" -> rates.bdt
            "bgn" -> rates.bgn
            "bhd" -> rates.bhd
            "bif" -> rates.bif
            "bmd" -> rates.bmd
            "bnb" -> rates.bnb
            "bnd" -> rates.bnd
            "bob" -> rates.bob
            "brl" -> rates.brl
            "bsd" -> rates.bsd
            "btc" -> rates.btc
            "btn" -> rates.btn
            "busd" -> rates.busd
            "bwp" -> rates.bwp
            "byn" -> rates.byn
            "byr" -> rates.byr
            "bzd" -> rates.bzd
            "cad" -> rates.cad
            "cdf" -> rates.cdf
            "chf" -> rates.chf
            "chz" -> rates.chz
            "clf" -> rates.clf
            "clp" -> rates.clp
            "cny" -> rates.cny
            "cop" -> rates.cop
            "crc" -> rates.crc
            "cro" -> rates.cro
            "cuc" -> rates.cuc
            "cup" -> rates.cup
            "cve" -> rates.cve
            "czk" -> rates.czk
            "dai" -> rates.dai
            "djf" -> rates.djf
            "dkk" -> rates.dkk
            "doge" -> rates.doge
            "dop" -> rates.dop
            "dot" -> rates.dot
            "dzd" -> rates.dzd
            "egld" -> rates.egld
            "egp" -> rates.egp
            "enj" -> rates.enj
            "ern" -> rates.ern
            "etb" -> rates.etb
            "etc" -> rates.etc
            "eth" -> rates.eth
            "eur" -> rates.eur
            "fil" -> rates.fil
            "fjd" -> rates.fjd
            "fkp" -> rates.fkp
            "ftt" -> rates.ftt
            "gbp" -> rates.gbp
            "gel" -> rates.gel
            "ggp" -> rates.ggp
            "ghs" -> rates.ghs
            "gip" -> rates.gip
            "gmd" -> rates.gmd
            "gnf" -> rates.gnf
            "grt" -> rates.grt
            "gtq" -> rates.gtq
            "gyd" -> rates.gyd
            "hkd" -> rates.hkd
            "hnl" -> rates.hnl
            "hrk" -> rates.hrk
            "htg" -> rates.htg
            "huf" -> rates.huf
            "icp" -> rates.icp
            "idr" -> rates.idr
            "ils" -> rates.ils
            "imp" -> rates.imp
            "inj" -> rates.inj
            "inr" -> rates.inr
            "iqd" -> rates.iqd
            "irr" -> rates.irr
            "isk" -> rates.isk
            "jep" -> rates.jep
            "jmd" -> rates.jmd
            "jod" -> rates.jod
            "jpy" -> rates.jpy
            "kes" -> rates.kes
            "kgs" -> rates.kgs
            "khr" -> rates.khr
            "kmf" -> rates.kmf
            "kpw" -> rates.kpw
            "krw" -> rates.krw
            "ksm" -> rates.ksm
            "kwd" -> rates.kwd
            "kyd" -> rates.kyd
            "kzt" -> rates.kzt
            "lak" -> rates.lak
            "lbp" -> rates.lbp
            "link" -> rates.link
            "lkr" -> rates.lkr
            "lrd" -> rates.lrd
            "lsl" -> rates.lsl
            "ltc" -> rates.ltc
            "ltl" -> rates.ltl
            "luna" -> rates.luna
            "lvl" -> rates.lvl
            "lyd" -> rates.lyd
            "mad" -> rates.mad
            "matic" -> rates.matic
            "mdl" -> rates.mdl
            "mga" -> rates.mga
            "mkd" -> rates.mkd
            "mmk" -> rates.mmk
            "mnt" -> rates.mnt
            "mop" -> rates.mop
            "mro" -> rates.mro
            "mur" -> rates.mur
            "mvr" -> rates.mvr
            "mwk" -> rates.mwk
            "mxn" -> rates.mxn
            "myr" -> rates.myr
            "mzn" -> rates.mzn
            "nad" -> rates.nad
            "ngn" -> rates.ngn
            "nio" -> rates.nio
            "nok" -> rates.nok
            "npr" -> rates.npr
            "nzd" -> rates.nzd
            "omr" -> rates.omr
            "one" -> rates.one
            "pab" -> rates.pab
            "pen" -> rates.pen
            "pgk" -> rates.pgk
            "php" -> rates.php
            "pkr" -> rates.pkr
            "pln" -> rates.pln
            "pyg" -> rates.pyg
            "qar" -> rates.qar
            "ron" -> rates.ron
            "rsd" -> rates.rsd
            "rub" -> rates.rub
            "rwf" -> rates.rwf
            "sar" -> rates.sar
            "sbd" -> rates.sbd
            "scr" -> rates.scr
            "sdg" -> rates.sdg
            "sek" -> rates.sek
            "sgd" -> rates.sgd
            "shib" -> rates.shib
            "shp" -> rates.shp
            "sll" -> rates.sll
            "sol" -> rates.sol
            "sos" -> rates.sos
            "srd" -> rates.srd
            "std" -> rates.std
            "svc" -> rates.svc
            "syp" -> rates.syp
            "szl" -> rates.szl
            "thb" -> rates.thb
            "theta" -> rates.theta
            "tjs" -> rates.tjs
            "tmt" -> rates.tmt
            "tnd" -> rates.tnd
            "top" -> rates.top
            "trx" -> rates.trx
            "try" -> rates.try1
            "ttd" -> rates.ttd
            "twd" -> rates.twd
            "tzs" -> rates.tzs
            "uah" -> rates.uah
            "ugx" -> rates.ugx
            "uni" -> rates.uni
            "usd" -> rates.usd
            "USDC" -> rates.usdc
            "usdt" -> rates.usdt
            "uyu" -> rates.uyu
            "uzs" -> rates.uzs
            "vef" -> rates.vef
            "vet" -> rates.vet
            "vnd" -> rates.vnd
            "vuv" -> rates.vuv
            "wbtc" -> rates.wbtc
            "wst" -> rates.wst
            "xaf" -> rates.xaf
            "xag" -> rates.xag
            "xau" -> rates.xau
            "xcd" -> rates.xcd
            "xdr" -> rates.xdr
            "xlm" -> rates.xlm
            "xmr" -> rates.xmr
            "xof" -> rates.xof
            "xpf" -> rates.xpf
            "xrp" -> rates.xrp
            "yer" -> rates.yer
            "zar" -> rates.zar
            "zmk" -> rates.zmk
            "zmw" -> rates.zmw
            "zwl" -> rates.zwl


            else -> rates.usd
        }
}