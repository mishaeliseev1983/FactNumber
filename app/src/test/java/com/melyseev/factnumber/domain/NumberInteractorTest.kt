package com.melyseev.factnumber.domain


import com.melyseev.factnumber.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumberInteractorTest {

    lateinit var numberInteractor: NumberInteractor
    lateinit var manageResources: BaseTest.TestManageResources
    lateinit var handleError: HandleError.HandleToString
    lateinit var repository: TestNumberRepository

    @Before
    fun setUp() {
        repository = TestNumberRepository()
        manageResources = BaseTest.TestManageResources()
        handleError = HandleError.HandleToString(manageResources)
        numberInteractor = NumberInteractor.Base(repository, handleError)
    }

    @Test
    fun allNumbers_success() = runBlocking {
        val number = numberInteractor.init(isFirstInit = true)

        assertEquals(0, repository.aboutNumberCalledCount)
        assertEquals(0, repository.allNumbers.size)
        assertEquals(NumberResult.Success(emptyList()), number)
    }

    //about number
    @Test
    fun aboutNumber_success() = runBlocking {
        repository.expectedNumberFact(NumberFact("1", "fact 1"))

        val number = numberInteractor.fetchAboutNumber("1")
        assertEquals(1, repository.aboutNumberCalledCount)
        assertEquals(1, repository.allNumbers.size)
        assertEquals(NumberFact("1", "fact 1"), repository.allNumbers[0])
        assertEquals(NumberResult.Success(listOf(NumberFact("1", "fact 1"))), number)
    }

    @Test
    fun aboutNumber_failure() = runBlocking {
        repository.expectingException = true
        manageResources.expectedStringResource("No internet connection")

        val number = numberInteractor.fetchAboutNumber("1")

        assertEquals(1, repository.aboutNumberCalledCount)
        assertEquals(0, repository.allNumbers.size)
        assertEquals(NumberResult.Error("No internet connection"), number)
    }

    //random number
    @Test
    fun aboutRandom_success() = runBlocking {
        repository.expectedNumberFact(NumberFact("1", "fact 1"))

        val number = numberInteractor.fetchAboutRandom()


        assertEquals(number, NumberResult.Success(listOf(NumberFact("1", "fact 1"))))
        assertEquals(1, repository.randomNumberCalledCount)
        assertEquals(1, repository.allNumbers.size)
        assertEquals(NumberFact("1", "fact 1"), repository.allNumbers[0])
    }

    @Test
    fun aboutRandom_failure() = runBlocking {
        repository.expectingException = true
        manageResources.expectedStringResource("No internet connection")

        val number = numberInteractor.fetchAboutRandom()

        assertEquals(number, NumberResult.Error("No internet connection"))
        assertEquals(1, repository.randomNumberCalledCount)
        assertEquals(0, repository.allNumbers.size)
    }


    class TestNumberRepository : NumberRepository {

        var aboutNumberCalledCount = 0
        var randomNumberCalledCount = 0
        var getNumberCalledCount = 0
        val allNumbers = mutableListOf<NumberFact>()

        var expectingException = false

        var numberFact = NumberFact("", "")
        fun expectedNumberFact(numFact: NumberFact) {
            numberFact = numFact
        }

        override suspend fun getNumbers(): List<NumberFact> {
            getNumberCalledCount++
            return allNumbers
        }

        override suspend fun getAboutNumber(numberAbout: String): NumberFact {
            aboutNumberCalledCount++
            if (expectingException)
                throw DomainException.NoInternetException

            allNumbers.add(numberFact)
            return numberFact
        }

        override suspend fun getRandomNumber(): NumberFact {
            randomNumberCalledCount++
            if (expectingException)
                throw DomainException.NoInternetException

            allNumbers.add(numberFact)
            return numberFact
        }

    }

}