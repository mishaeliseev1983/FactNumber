package com.melyseev.factnumber.di.modules

import com.melyseev.factnumber.data.cloud.CloudDataSource
import com.melyseev.factnumber.data.cloud.CloudNumberService
import com.melyseev.factnumber.data.cloud.CloudeModule
import com.melyseev.factnumber.di.ApplicationScope
import com.melyseev.factnumber.domain.NumberFact
import com.melyseev.factnumber.presentation.NumberUI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    fun provideNumberService(): CloudNumberService{
       return CloudeModule.Base().getService(CloudNumberService::class.java)
    }

    @Provides
    fun provideCloudDataSource(param: CloudDataSource.Base): CloudDataSource{
        return param
    }

    @Provides
    fun provideMapperNumberFactToNumberUI(): NumberFact.Mapper<NumberUI>{
        return object : NumberFact.Mapper<NumberUI>{
            override fun mapToUI(id: String, fact: String): NumberUI {
                return NumberUI(id = id, fact = fact)
            }
        }
    }

}