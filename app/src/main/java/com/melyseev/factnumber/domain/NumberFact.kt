package com.melyseev.factnumber.domain

data class NumberFact(val id: String, val fact: String){

    fun <T> mapToUI(mapper: Mapper<T>): T = mapper.mapToUI(id, fact)
    interface Mapper<T>{
            fun mapToUI(id: String, fact: String): T
    }
}