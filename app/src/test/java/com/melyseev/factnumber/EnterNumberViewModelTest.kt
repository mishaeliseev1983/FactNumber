package com.melyseev.factnumber

import com.melyseev.factnumber.domain.*
import com.melyseev.factnumber.presentation.UIState
import com.melyseev.factnumber.presentation.EnterNumberViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class EnterNumberViewModelTest {
    lateinit var communication: BaseTest.TestNumberFactCommunication
    lateinit var interactor: BaseTest.TestNumberFactInteractor
    lateinit var numberResultMapper : NumberResultMapper
    lateinit var numberUIMapper: NumberUIMapper
    lateinit var enterNumberViewModel: EnterNumberViewModel
    lateinit var manageResources: BaseTest.TestManageResources
    lateinit var dispatchersList: TestDisptachersList


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        dispatchersList = TestDisptachersList()
        communication = BaseTest.TestNumberFactCommunication()
        manageResources = BaseTest.TestManageResources()


        interactor = BaseTest.TestNumberFactInteractor()

        numberUIMapper = NumberUIMapper()
        numberResultMapper = NumberResultMapper(communication, numberUIMapper)


        enterNumberViewModel = EnterNumberViewModel(dispatchersList, communication, interactor, numberResultMapper, manageResources)
    }


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun init() = runBlocking {

        interactor.changeExpectedResult(NumberResult.Success(emptyList()))
        enterNumberViewModel.init(true)


        assertEquals(1, interactor.initCalledList.size)
        assertEquals(NumberResult.Success(emptyList()), interactor.result)

        assertEquals(2, communication.progressCalledList.size)
        assertEquals(true, communication.progressCalledList[0])
        assertEquals(false, communication.progressCalledList[1])
        assertEquals(1, communication.timesCalledNumber)
    }

    @Test
    fun entered_empty_number() = runBlocking{
        manageResources.expectedStringResource("Please enter a number")
        enterNumberViewModel.fetchAboutNumber("")

        assertEquals(0, interactor.getFactAboutCalledList.size)
        assertEquals(0, communication.progressCalledList.size)
        assertEquals(UIState.Failure("Please enter a number"), communication.stateCalledList[0])
        assertEquals(0, communication.timesCalledNumber)
    }

    @Test
    fun fetch_some_number() = runBlocking {
        enterNumberViewModel.fetchAboutNumber("1")

        assertEquals(1, interactor.getFactAboutCalledList.size)
        assertEquals(2, communication.progressCalledList.size)
        assertEquals(UIState.Success, communication.stateCalledList[0])
        assertEquals(1, communication.timesCalledNumber)
    }

    @Test
    fun fetch_random_number() = runBlocking {
        enterNumberViewModel.fetchRandomNumber()

        assertEquals(1, interactor.getFactRandomCalledList.size)

        assertEquals(2, communication.progressCalledList.size)
        assertEquals(UIState.Success, communication.stateCalledList[0])
        assertEquals(1, communication.timesCalledNumber)
    }
}

class TestDisptachersList: EnterNumberViewModel.DispatchersList {
    override fun io(): CoroutineDispatcher {
        return TestCoroutineDispatcher()
    }

    override fun ui(): CoroutineDispatcher {
        return StandardTestDispatcher()
    }

}