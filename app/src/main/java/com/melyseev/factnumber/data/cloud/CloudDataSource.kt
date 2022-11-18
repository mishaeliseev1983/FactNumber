package com.melyseev.factnumber.data.cloud

import com.melyseev.factnumber.data.NumberData

interface CloudDataSource {
    suspend fun number(number: String): NumberData
    suspend fun randomNumber(): NumberData


    class Base(service: CloudNumberService): CloudDataSource{
        override suspend fun number(number: String): NumberData {
            TODO()
        }

        override suspend fun randomNumber(): NumberData {
            TODO("Not yet implemented")
        }

    }
}