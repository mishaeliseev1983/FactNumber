package com.melyseev.factnumber.data

interface CacheDataSource {
    suspend fun getNumbers(): List<NumberData>
    suspend fun number(number: String): NumberData
    suspend fun contains(number: String): Boolean
    suspend fun saveNumber(number: String)
}