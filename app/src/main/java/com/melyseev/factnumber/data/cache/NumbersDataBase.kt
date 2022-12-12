package com.melyseev.factnumber.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumberCache::class], version = 1, exportSchema = false)
abstract class NumbersDataBase : RoomDatabase() {
    abstract fun numbersDao(): NumbersDao
}