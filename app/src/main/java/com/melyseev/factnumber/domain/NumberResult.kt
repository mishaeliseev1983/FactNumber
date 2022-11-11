package com.melyseev.factnumber.domain

sealed class NumberResult{

    abstract fun <T> map(mapper: Mapper<T> ): T
    interface  Mapper<T> {
        fun mapToUi(listNumberFact: List<NumberFact>, message: String): T
    }

    data class Success(val list:List<NumberFact>) : NumberResult(){
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.mapToUi(listNumberFact = list, "")
        }
    }
    data class Error(val message: String): NumberResult() {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.mapToUi(emptyList(), message)
        }
    }
}
