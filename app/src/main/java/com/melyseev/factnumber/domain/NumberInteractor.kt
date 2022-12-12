package com.melyseev.factnumber.domain

import javax.inject.Inject

interface NumberInteractor {
    suspend fun init(): NumberResult
    suspend fun fetchAboutRandom(): NumberResult
    suspend fun fetchAboutNumber(number: String): NumberResult


    class Base @Inject constructor(private val repository: NumberRepository,
                                   private val handleError: HandleError.HandleToString):NumberInteractor{
        override suspend fun init(): NumberResult {
            return NumberResult.Success(repository.getNumbers())
        }

        override suspend fun fetchAboutRandom(): NumberResult {

            var numbers: List<NumberFact>
            try {
                repository.getRandomNumber()
                numbers = repository.getNumbers()
            }catch (e : DomainException){
                val errorString = handleError.handle(e)
                return NumberResult.Error(  errorString )
            }
            return NumberResult.Success(numbers)
        }

        override suspend fun fetchAboutNumber(number: String): NumberResult {
            var numbers: List<NumberFact>
            try {
                repository.getAboutNumber(number)
                numbers = repository.getNumbers()
            }catch (e : DomainException){
                val errorString = handleError.handle(e)
                return NumberResult.Error(errorString)
            }
            return NumberResult.Success(numbers)
        }

    }

}