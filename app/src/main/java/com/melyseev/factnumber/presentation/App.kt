package com.melyseev.factnumber.presentation

import android.app.Application
import com.melyseev.factnumber.data.cloud.CloudDataSource
import com.melyseev.factnumber.data.cloud.CloudNumberService
import com.melyseev.factnumber.data.cloud.CloudeModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        val keySearchNumber =  "X-Numbers-API-Number"
        val service = CloudeModule.Base().getService(CloudNumberService::class.java)


        GlobalScope.launch {

            val  cloudNumberService = CloudDataSource.Base(service = service)
            val n = cloudNumberService.randomNumber()

            println("numberData = ${n.id}  ${n.fact}")
        }

    }
}