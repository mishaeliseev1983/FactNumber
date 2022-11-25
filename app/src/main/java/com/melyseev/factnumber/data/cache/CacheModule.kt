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
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        override fun provideDataBase(): NumbersDataBase = database

        companion object{
            const val DATABASE_NAME = "numbers_db"
        }
    }
}