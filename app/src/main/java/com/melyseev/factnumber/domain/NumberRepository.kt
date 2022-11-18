package com.melyseev.factnumber.domain

interface NumberRepository {
    suspend fun getNumbers(): List<NumberFact>
    suspend fun getAboutNumber(number: String): NumberFact
    suspend fun getRandomNumber(): NumberFact
}