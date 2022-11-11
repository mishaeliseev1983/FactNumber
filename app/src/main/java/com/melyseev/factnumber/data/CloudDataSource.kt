package com.melyseev.factnumber.data

interface CloudDataSource {
    suspend fun number(number: String): NumberData
    suspend fun randomNumber(): NumberData
}