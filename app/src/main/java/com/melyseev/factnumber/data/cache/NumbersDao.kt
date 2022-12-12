package com.melyseev.factnumber.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.melyseev.factnumber.data.cache.NumberCache.Companion.TABLE_NUMBERS


@Dao
interface NumbersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NumberCache)

    @Query("SELECT * FROM $TABLE_NUMBERS ORDER BY DATE DESC")
    fun getAllNumbers(): List<NumberCache>

    @Query("SELECT * FROM $TABLE_NUMBERS WHERE id = :numberId")
    fun getNumber(numberId: String): NumberCache?
}