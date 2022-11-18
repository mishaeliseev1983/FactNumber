package com.melyseev.factnumber.data

import com.melyseev.factnumber.data.cache.LocalDataSource
import com.melyseev.factnumber.data.cloud.CloudDataSource
import com.melyseev.factnumber.domain.NumberFact
import com.melyseev.factnumber.domain.NumberRepository
import kotlinx.coroutines.runBlocking

class NumberRepositoryBase(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource,
    private val mapperToNumberFact: MapperToNumberFact,
    private val handleErrorToDomainException: HandleErrorToDomainException
) : NumberRepository {

    override suspend fun getNumbers(): List<NumberFact> {
        return localDataSource.getNumbers().map {
            it.mapToDomain(mapperToNumberFact)
        }
    }

    override suspend fun getAboutNumber(number: String): NumberFact = runBlocking {
        var numberData: NumberData
        try {
            if (localDataSource.contains(number))
                numberData = localDataSource.number(number)
            else
                numberData = cloudDataSource.number(number = number)
            localDataSource.saveNumber(numberData)
            return@runBlocking numberData.mapToDomain(mapperToNumberFact)
        } catch (e: Exception) {
            throw handleErrorToDomainException.handle(e)
        }
    }

    override suspend fun getRandomNumber(): NumberFact = runBlocking {
        try {
            val randomNumberData = cloudDataSource.randomNumber()
            localDataSource.saveNumber(randomNumberData)
            return@runBlocking randomNumberData.mapToDomain(mapperToNumberFact)
        } catch (e: Exception) {
            throw handleErrorToDomainException.handle(e)
        }
    }

}