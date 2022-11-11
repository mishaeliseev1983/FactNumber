package com.melyseev.factnumber.domain

import com.melyseev.factnumber.BaseTest
import com.melyseev.factnumber.EnterNumberViewModelTest
import com.melyseev.factnumber.presentation.NumberCommunication
import com.melyseev.factnumber.presentation.NumberUI
import com.melyseev.factnumber.presentation.UIState

import org.junit.Assert.*
import org.junit.Test

class NumberResultMapperTest : BaseTest(){

    @Test
    fun test_failure_no_internet_connection() {

        val communication = TestNumberFactCommunication()

        /*
        val mapper = object : NumberFact.Mapper<NumberUI>{
            override fun mapToUI(id: String, fact: String): NumberUI {
                return NumberUI(id, fact)
            }
        }

         */
        val numberResultMapper = NumberResultMapper(communication, NumberUIMapper())

        numberResultMapper.mapToUi(emptyList(), "no internet connection")
        assertEquals(UIState.Failure("no internet connection"), communication.stateCalledList[0])
    }


    @Test
    fun test_success() {

        val communication = TestNumberFactCommunication()

        val mapper = object : NumberFact.Mapper<NumberUI>{
            override fun mapToUI(id: String, fact: String): NumberUI {
                return NumberUI(id, fact)
            }
        }
        val numberResultMapper = NumberResultMapper(communication, mapper)

        val listNumberFact = listOf(NumberFact("1", "fact 1"))
        numberResultMapper.mapToUi(listNumberFact,  "")
        assertEquals(UIState.Success, communication.stateCalledList[0])
    }

}