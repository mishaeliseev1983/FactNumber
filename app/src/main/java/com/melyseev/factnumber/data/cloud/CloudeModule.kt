package com.melyseev.factnumber.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

interface CloudeModule {
    fun <T> getService(clazz: Class<T>): T

    class Base @Inject constructor()  : CloudeModule {
        override fun <T> getService(clazz: Class<T>): T {

            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit
                .Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(clazz)
        }

        companion object Constants {
            const val BASE_URL = "http://numbersapi.com/"
        }
    }
}