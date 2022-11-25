package com.melyseev.factnumber.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


interface NumberCommunication : ObserveCommunication{
    fun showProgress(show: Boolean)
    fun showNumbersList(numbersList: List<NumberUI>)
    fun showState(uiState: UIState)

    class Base(
        private val progressCommunication: ProgressCommunication,
        private val numbersListCommunication: NumbersListCommunication,
        private val numberStateCommunication: NumberStateCommunication
    ) : NumberCommunication {
        override fun showProgress(show: Boolean) {
            progressCommunication.change(show)
        }

        override fun showNumbersList(numbersList: List<NumberUI>) {
            numbersListCommunication.change(numbersList)
        }

        override fun showState(uiState: UIState) {
            numberStateCommunication.change(uiState)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
            progressCommunication.observe(owner, observer)
        }

        override fun observeNumberList(owner: LifecycleOwner, observer: Observer<List<NumberUI>>) {
            numbersListCommunication.observe(owner, observer)
        }

        override fun observeState(owner: LifecycleOwner, observer: Observer<UIState>) {
            numberStateCommunication.observe(owner, observer)
        }
    }
}