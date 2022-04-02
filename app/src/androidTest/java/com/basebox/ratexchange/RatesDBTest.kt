package com.basebox.ratexchange

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.basebox.ratexchange.data.local.dao.RatesDao
import com.basebox.ratexchange.data.local.database.RatesDB
import com.basebox.ratexchange.data.local.entity.Motd
import com.basebox.ratexchange.data.local.entity.RatesModel
import com.basebox.ratexchange.data.remote.RateResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RatesDBTest {

    private lateinit var ratesDao: RatesDao
    private lateinit var db: RatesDB

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, RatesDB::class.java)
            .allowMainThreadQueries().build()
        ratesDao = db.ratesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRate() {
        val base = "usd"
        val date = "10th, May"
        val motd = Motd("Hey", "htto://martini.com")
        val rates = RatesModel(
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0
        )
        val singleRate = RateResponse(base, date, motd, rates, true)
        ratesDao.insertRate(singleRate)
        val firstRate = ratesDao.getRate("usd")
        firstRate.rates.aUD.let {
            assert(it.equals(0.0))
        }
    }
}