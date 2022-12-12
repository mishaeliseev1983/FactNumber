package com.melyseev.factnumber.domain

import javax.inject.Inject


data class NumberFact @Inject constructor(val id: String, val fact: String){

    fun <T> mapToUI(mapper: Mapper<T>): T = mapper.mapToUI(id, fact)
    interface Mapper<T>{
            fun mapToUI(id: String, fact: String): T
    }
}