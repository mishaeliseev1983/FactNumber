package com.melyseev.factnumber.data.cache

import com.melyseev.factnumber.data.NumberData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock



interface CacheDataSource {
    suspend fun getNumbers(): List<NumberData>
    suspend fun number(number: String): NumberData
    suspend fun contains(number: String): Boolean
    suspend fun saveNumber(number: NumberData)


    class Base(private val dao: NumbersDao,
                                       private val mapperToNumberCache: NumberData.Mapper<NumberCache>): CacheDataSource{

        private val mutex = Mutex()
        override suspend fun getNumbers(): List<NumberData> = mutex.withLock{
            return dao.getAllNumbers().map {
                NumberData(id = it.id, fact = it.fact)
            }
        }

        override suspend fun number(number: String): NumberData = mutex.withLock{
           val numberCache = dao.getNumber(number)?:NumberCache(id="", fact = "", date = 0)
           return NumberData(id = numberCache.id, fact =  numberCache.fact)
        }

        override suspend fun contains(number: String): Boolean = mutex.withLock{
           val numberCache = dao.getNumber(numberId = number)
           return numberCache != null
        }

        override suspend fun saveNumber(number: NumberData) = mutex.withLock{
           val numberCache = mapperToNumberCache.map(id = number.id, fact = number.fact)
           dao.insert(numberCache)
        }

    }
}