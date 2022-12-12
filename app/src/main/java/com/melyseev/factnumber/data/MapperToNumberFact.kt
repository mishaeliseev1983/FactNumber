package com.melyseev.factnumber.data

import com.melyseev.factnumber.domain.NumberFact
import javax.inject.Inject

class MapperToNumberFact @Inject constructor(): NumberData.Mapper<NumberFact> {
    override fun map(id: String, fact: String): NumberFact = NumberFact(id = id, fact = fact)
}