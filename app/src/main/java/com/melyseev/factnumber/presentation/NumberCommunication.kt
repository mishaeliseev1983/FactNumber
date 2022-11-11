package com.melyseev.factnumber.presentation



interface NumberCommunication {
    fun showProgress(show: Boolean)
    fun showNumbersList(numbersList: List<NumberUI>)
    fun showState(uiState: UIState)
}