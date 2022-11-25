package com.melyseev.factnumber.data.cloud

import com.melyseev.factnumber.data.NumberData

interface CloudDataSource {
    suspend fun number(number: String): NumberData
    suspend fun randomNumber(): NumberData


    class Base(private val service: CloudNumberService): CloudDataSource{
        override suspend fun number(number: String): NumberData {
            val fact = service.fact(number)
            return NumberData(id = number, fact = fact)
        }

        override suspend fun randomNumber(): NumberData {
            val str = service.random()

            val valueNumber = str.headers()[HEADER_FOR_SEARCH_NUMBER].orEmpty()
            val body = str.body().orEmpty()
            return NumberData( id = valueNumber, fact =  body)
        }

        companion object {
            const val HEADER_FOR_SEARCH_NUMBER = "X-Numbers-API-Number"
        }
    }
}