package com.melyseev.factnumber.domain

interface NumberRepository {
    fun getNumbers(): List<NumberFact>
    fun getAboutNumber(number: String): NumberFact
    fun getRandomNumber(): NumberFact

    class Base: NumberRepository{
        override fun getNumbers(): List<NumberFact> {
            TODO("Not yet implemented")
        }

        override fun getAboutNumber(number: String): NumberFact {
            TODO("Not yet implemented")
        }

        override fun getRandomNumber(): NumberFact {
            TODO("Not yet implemented")
        }

    }
}