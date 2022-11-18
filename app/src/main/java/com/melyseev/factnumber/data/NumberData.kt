package com.melyseev.factnumber.data

data class NumberData(val id: String, val fact: String){

    fun <T> mapToDomain(mapper: Mapper<T>):T = mapper.map(id, fact)
    interface Mapper<T>{
        fun map(id: String, fact: String): T
    }
}