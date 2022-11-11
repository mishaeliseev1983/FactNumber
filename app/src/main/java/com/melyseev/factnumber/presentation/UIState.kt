package com.melyseev.factnumber.presentation

sealed class UIState {
    object Success : UIState()
    data class Failure(val message: String) : UIState()
}