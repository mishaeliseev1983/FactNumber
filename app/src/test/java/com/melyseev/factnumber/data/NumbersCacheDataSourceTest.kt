package com.melyseev.factnumber.data

import com.melyseev.factnumber.data.cache.CacheDataSource
import com.melyseev.factnumber.data.cache.NumberCache
import com.melyseev.factnumber.data.cache.NumbersDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumbersCacheDataSourceTest {



        @Before
    fun setUp(){

    }


    @Test
    fun first_time_empty_list_numbers() = runBlocking{

        val dao = TestDao()
        val numbersCacheDataSource = CacheDataSource.Base(dao = dao, mapperToNumberCache = MapperTest(7))

        val numbers = numbersCacheDataSource.getNumbers()
        assertEquals(emptyList<NumberData>(), numbers)
    }

    @Test
    fun contains_element() = runBlocking {

        val dao = TestDao()
        val numbersCacheDataSource = CacheDataSource.Base(dao = dao, mapperToNumberCache = MapperTest(7))

        dao.numbers.add(NumberCache(id = "1", fact = "fact 1", date = 1))
        dao.numbers.add(NumberCache(id = "2", fact = "fact 2", date = 2))
        dao.numbers.add(NumberCache(id = "4", fact = "fact 4", date = 4))

        assertEquals(true, numbersCacheDataSource.contains("1"))
    }


    class TestDao: NumbersDao{

        val numbers = mutableListOf<NumberCache>()

        override fun insert(entity: NumberCache) {

            TODO()
        }

        override fun getAllNumbers(): List<NumberCache> {
            return numbers
        }

        override fun getNumber(numberId: String): NumberCache? {
          return numbers.find{
               it.id == numberId
           }
        }
    }

    class MapperTest(val date: Long): NumberData.Mapper<NumberCache>{
        override fun map(id: String, fact: String): NumberCache {
            return  NumberCache(id = id, fact = fact, date = date)
        }

    }

}