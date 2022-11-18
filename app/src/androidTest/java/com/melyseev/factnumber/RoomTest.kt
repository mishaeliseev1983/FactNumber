package com.melyseev.factnumber

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.melyseev.factnumber.data.cache.NumberCache
import com.melyseev.factnumber.data.cache.NumbersDao
import com.melyseev.factnumber.data.cache.NumbersDataBase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: NumbersDataBase
    private lateinit var dao: NumbersDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, NumbersDataBase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.numbersDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_add_and_check() {
        val number = NumberCache("42", "fact42", 12)
        assertEquals(null, dao.getNumber("42"))
        dao.insert(number)
        val actualList = dao.getAllNumbers()
        assertEquals(number, actualList[0])
        val actual = dao.getNumber("42")
        assertEquals(number, actual)
    }

    @Test
    fun test_add_2_times() {
        val number = NumberCache("42", "fact42", 12)
        dao.insert(number)
        var actualList = dao.getAllNumbers()
        assertEquals(number, actualList[0])

        val new = NumberCache("42", "fact42", 15)
        dao.insert(new)
        actualList = dao.getAllNumbers()
        assertEquals(1, actualList.size)
        assertEquals(new, actualList[0])
    }
}