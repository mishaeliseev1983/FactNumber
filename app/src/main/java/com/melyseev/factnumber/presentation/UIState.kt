package com.melyseev.factnumber.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class UIState {

    abstract fun apply(layout: TextInputLayout, editText: TextInputEditText)

    object Success : UIState() {
        override fun apply(layout: TextInputLayout, editText: TextInputEditText) {
            editText.setText("")
        }

    }

    /*
    data class Failure(val message: String) : UIState() {
        override fun apply(layout: TextInputLayout, editText: TextInputEditText) {
            layout.isErrorEnabled = true
            layout.error = message
        }
    }
    */
    data class Failure(val message: String): ClearErrorAbstract(enabled = true, error =  message)
    class Clear: ClearErrorAbstract(enabled = false, error= "")

    abstract class ClearErrorAbstract(
       val enabled : Boolean,
       val error: String) : UIState(){
        override fun apply(layout: TextInputLayout, editText: TextInputEditText) {
            layout.error = error
            layout.isErrorEnabled = enabled
        }
    }


}