package com.melyseev.factnumber.domain

import com.melyseev.factnumber.presentation.NumberUI

class NumberUIMapper: NumberFact.Mapper<NumberUI>{
    override fun mapToUI(id: String, fact: String): NumberUI = NumberUI(id, fact)
}