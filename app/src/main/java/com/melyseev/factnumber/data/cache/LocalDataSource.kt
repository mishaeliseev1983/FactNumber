package com.melyseev.factnumber.data.cache

import com.melyseev.factnumber.data.NumberData

interface LocalDataSource {
    suspend fun getNumbers(): List<NumberData>
    suspend fun number(number: String): NumberData
    suspend fun contains(number: String): Boolean
    suspend fun saveNumber(number: NumberData)
}