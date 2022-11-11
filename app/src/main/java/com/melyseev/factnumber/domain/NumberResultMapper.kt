package com.melyseev.factnumber.domain

import com.melyseev.factnumber.presentation.NumberCommunication
import com.melyseev.factnumber.presentation.NumberUI
import com.melyseev.factnumber.presentation.UIState

class NumberResultMapper(
   private val communications: NumberCommunication,
   private val mapperNumberFactToNumberUI: NumberFact.Mapper<NumberUI>
    ): NumberResult.Mapper<Unit>{
    override fun mapToUi(listNumberFact: List<NumberFact>, message: String)
    = communications.showState(
        if(message.isEmpty()){
            val list =  listNumberFact.map {
                it.mapToUI(mapperNumberFactToNumberUI)
            }
            communications.showNumbersList(list)
            UIState.Success
        } else {
            UIState.Failure(message)
        }
        )
}
