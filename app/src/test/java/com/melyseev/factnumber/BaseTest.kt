package com.melyseev.factnumber

import com.melyseev.factnumber.domain.NumberFact
import com.melyseev.factnumber.domain.NumberInteractor
import com.melyseev.factnumber.domain.NumberRepository
import com.melyseev.factnumber.domain.NumberResult
import com.melyseev.factnumber.presentation.ManageResources
import com.melyseev.factnumber.presentation.NumberCommunication
import com.melyseev.factnumber.presentation.NumberUI
import com.melyseev.factnumber.presentation.UIState

abstract class BaseTest {


    class TestNumberFactCommunication: NumberCommunication {

        val progressCalledList =  mutableListOf<Boolean>()
        var timesCalledNumber = 0
        val stateCalledList = mutableListOf<UIState>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showNumbersList(numbersList: List<NumberUI>) {
            timesCalledNumber++
        }

        override fun showState(uiState: UIState) {
            stateCalledList.add(uiState)
        }
    }

    class TestNumberFactInteractor: NumberInteractor {

        var result: NumberResult = NumberResult.Success(emptyList())
        val initCalledList = mutableListOf<NumberResult>()
        val getFactRandomCalledList = mutableListOf<NumberResult>()
        val getFactAboutCalledList = mutableListOf<NumberResult>()

        fun changeExpectedResult(numberResult: NumberResult){
            result = numberResult
        }
        override suspend fun init(isFirst: Boolean): NumberResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun fetchAboutRandom(): NumberResult {
            getFactRandomCalledList.add(result)
            return result
        }

        override suspend fun fetchAboutNumber(num: String): NumberResult {
            getFactAboutCalledList.add(result)
            return result
        }
    }

    class TestManageResources: ManageResources{
        var stroka: String = ""
        fun expectedStringResource(value: String) {stroka = value}
        override fun string(id: Int): String {
           return stroka
        }
    }

}