package com.melyseev.factnumber.data.cloud

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface CloudNumberService {

    @GET("{id}")
    suspend fun fact(@Path("id") id: String): String

    @GET("/random/math")
    suspend fun random(): Response<String>

   // http://numbersapi.com/number/type

   // http://numbersapi.com/random/math

}
