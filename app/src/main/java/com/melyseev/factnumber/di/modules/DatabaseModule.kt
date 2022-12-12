package com.melyseev.factnumber.di

import com.melyseev.factnumber.data.NumberData
import com.melyseev.factnumber.data.cache.*
import com.melyseev.factnumber.presentation.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    fun provideDataBase(app: App): NumbersDataBase {
        return CacheModule.Base(context = app).provideDataBase()
    }

    @Provides
    fun provideDao(numbersDataBase: NumbersDataBase): NumbersDao {
        return numbersDataBase.numbersDao()
    }

    @Provides
    fun provideMapperToNumberCache(): NumberData.Mapper<NumberCache> {
        return object : NumberData.Mapper<NumberCache> {
            override fun map(id: String, fact: String): NumberCache {
                return NumberCache(id = id, fact = fact, date = System.currentTimeMillis())
            }
        }
    }

    @Provides
    fun provideCacheDataSource(param: CacheDataSource.Base): CacheDataSource{
        return param
    }
}