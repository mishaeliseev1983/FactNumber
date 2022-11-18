package com.melyseev.factnumber.data.cache

import android.content.Context
import androidx.room.Room

interface  CacheModule {

    fun provideDataBase(): NumbersDataBase

    class Base(private val context: Context) : CacheModule {

        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                NumbersDataBase::class.java,
                NumberCache.TABLE_NUMBERS
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDataBase(): NumbersDataBase = database
    }
}