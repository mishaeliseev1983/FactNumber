package com.melyseev.factnumber.domain

import com.melyseev.factnumber.data.*
import com.melyseev.factnumber.data.cache.LocalDataSource
import com.melyseev.factnumber.data.cloud.CloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class NumberRepositoryTest {

    lateinit var repository: NumberRepository
    lateinit var cloudDataSource: TestCloudDataSource
    lateinit var localDataSource: TestLocalDataSource
    lateinit var mapperToNumberFact: MapperToNumberFact
    lateinit var errorToDomainException: HandleErrorToDomainException
    @Before
    fun setUp() {
        cloudDataSource = TestCloudDataSource()
        localDataSource = TestLocalDataSource()
        mapperToNumberFact = MapperToNumberFact()
        errorToDomainException = HandleErrorToDomainException()
        repository = NumberRepositoryBase(localDataSource, cloudDataSource, mapperToNumberFact, errorToDomainException)
    }

    @Test
    fun success_get_allNumbers() = runBlocking {
        val actual = repository.getNumbers()
        assertEquals(1, localDataSource.getNumbersCalledCount)
        assertEquals(actual, localDataSource.numbers)
    }

    @Test
    fun success_get_number_not_cache() = runBlocking {
        cloudDataSource.setExpectedNumber(NumberData("2", "2 cloud"))
        localDataSource.setExpectedNumberData(NumberData("3", "fact 3"))

        val actual = repository.getAboutNumber("2")
        assertEquals(actual, cloudDataSource.expectedNumberData.mapToDomain(mapperToNumberFact))

        assertEquals(1, localDataSource.numbers.size)
        assertEquals(0, cloudDataSource.getRandomCalledCount)
        assertEquals(1, localDataSource.saveNumberCalledList.size)
        assertEquals(1, cloudDataSource.getNumberCalledCount)
        assertEquals(0, localDataSource.getNumberCalledList.size)
        assertEquals(1, localDataSource.containsNumberCalledList.size)
    }

    @Test
    fun success_get_number_from_yes_cached() = runBlocking {
        cloudDataSource.setExpectedNumber(NumberData("2", "2 cloud"))
        localDataSource.setExpectedNumberData(NumberData("3", "fact 3"))


        val actual = repository.getAboutNumber("3")
        assertEquals(actual, localDataSource.expectedNumber.mapToDomain(mapperToNumberFact))

        assertEquals(1, localDataSource.numbers.size)
        assertEquals(0, cloudDataSource.getRandomCalledCount)
        assertEquals(1, localDataSource.saveNumberCalledList.size)
        assertEquals(0, cloudDataSource.getNumberCalledCount)
        assertEquals(1, localDataSource.getNumberCalledList.size)
        assertEquals(1, localDataSource.containsNumberCalledList.size)
    }

    @Test(expected = DomainException.NoInternetException::class)
    fun error_get_number(): Unit = runBlocking {
        cloudDataSource.expectedError = true
        repository.getAboutNumber("3")
    }


    @Test
    fun success_get_random() = runBlocking {

        cloudDataSource.expectedError = false
        cloudDataSource.setExpectedRandomNumberData(expectRandomValue = NumberData("1", "cloud 1"))
        val actual = repository.getRandomNumber()
        val expected = cloudDataSource.expectedRandomNumber.mapToDomain(mapperToNumberFact)

        assertEquals(expected, actual)
        assertEquals(1, cloudDataSource.getRandomCalledCount)
        assertEquals(1, localDataSource.saveNumberCalledList.size)
        assertEquals(0, cloudDataSource.getNumberCalledCount)
        assertEquals(0, localDataSource.containsNumberCalledList.size)
    }

    @Test(expected = DomainException.NoInternetException::class)
    fun error_get_random(): Unit = runBlocking {
        cloudDataSource.expectedError = true
        repository.getRandomNumber()
    }


    //wrapper retrofit
    class TestCloudDataSource : CloudDataSource {

        var expectedError = false

        var getRandomCalledCount = 0
        var expectedRandomNumber = NumberData("1", "cloud 1")
        fun setExpectedRandomNumberData(expectRandomValue: NumberData) {
            expectedRandomNumber = expectRandomValue
        }

        var getNumberCalledCount = 0
        var expectedNumberData = NumberData("2", "cloud 2")
        fun setExpectedNumber(expectRandomValue: NumberData) {
            expectedNumberData = expectRandomValue
        }

        override suspend fun number(number: String): NumberData {
            getNumberCalledCount++
            if (expectedError)
                throw UnknownHostException()
            return expectedNumberData
        }

        override suspend fun randomNumber(): NumberData {
            getRandomCalledCount++
            if (expectedError)
                throw UnknownHostException()
            return expectedRandomNumber
        }

    }

    //wrapper room
    class TestLocalDataSource : LocalDataSource {

        val numbers = mutableListOf<NumberData>()
        var getNumbersCalledCount = 0
        var getNumberCalledList = mutableListOf<String>()
        var saveNumberCalledList = mutableListOf<NumberData>()
        var containsNumberCalledList = mutableListOf<String>()
        var expectedNumber = NumberData("", "")

        fun setExpectedNumberData(numberData: NumberData) {
            expectedNumber = numberData
            numbers.add(numberData)
        }

        override suspend fun getNumbers(): List<NumberData> {
            getNumbersCalledCount++
            return numbers
        }

        override suspend fun number(number: String): NumberData {
            getNumberCalledList.add(number)
            return expectedNumber
        }

        override suspend fun contains(number: String): Boolean {
            containsNumberCalledList.add(number)
            return numbers.map { it.id }.contains(number)
        }

        override suspend fun saveNumber(number: NumberData) {
            saveNumberCalledList.add(number)
        }
    }

}
