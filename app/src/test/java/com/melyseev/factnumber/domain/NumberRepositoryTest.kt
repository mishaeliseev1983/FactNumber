package com.melyseev.factnumber.domain

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumberRepositoryTest{


    lateinit var repository: NumberRepository
    lateinit var cloudDataSource: TestCloudDataSource
    lateinit var cloudCacheSource: TestCacheDataSource

    @Before
    fun setUp(){

    }

    @Test
    fun success_get_numbers(){

    }


    //wrapper retrofit
    class TestCloudDataSource {
        fun randomNumber(): NumberData
        fun number(number: String): NumberData
    }

    //wrapper room
    class TestCacheDataSource: CacheDataSource {
        fun getNumbers(): List<NumberData>
        fun number(number: String): NumberData
        fun contains(number: String): Boolean
        fun saveNumber(number: String)
    }


}