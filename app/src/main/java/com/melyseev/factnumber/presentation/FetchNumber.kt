package com.melyseev.factnumber.presentation

interface FetchNumber {
    fun init(isFirstTime: Boolean)
    fun fetchAboutNumber(number: String)
    fun fetchRandomNumber()
}