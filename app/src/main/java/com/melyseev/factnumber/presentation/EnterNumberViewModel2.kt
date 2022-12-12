package com.melyseev.factnumber.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.factnumber.domain.NumberInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterNumberViewModel2 @Inject constructor(
    private val dispatchersList: DispatchersList,
    private val communication: NumberCommunication,
    private val interactor: NumberInteractor
) : ViewModel(), FetchNumber, ObserveCommunication {


    override fun init(isFirstTime: Boolean) {
        if (isFirstTime) {
            communication.showProgress(true)
            viewModelScope.launch(dispatchersList.io()) {

                delay(2_000)
                communication.showProgress(false)
            }
        }
    }

    override fun fetchAboutNumber(number: String) {
        if(number.isEmpty()){
            communication.showState(UIState.Failure("1111111"))
        }else{

            communication.showProgress(true)
            viewModelScope.launch(dispatchersList.io()) {
                delay(2_000)
                communication.showProgress(false)
            }
        }
    }

    override fun fetchRandomNumber() {
        communication.showProgress(true)
        viewModelScope.launch(dispatchersList.io()) {
            delay(2_000)
            communication.showProgress(false)
        }

    }


    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
        communication.observeProgress(owner, observer)
    }

    override fun observeNumberList(owner: LifecycleOwner, observer: Observer<List<NumberUI>>) {
        communication.observeNumberList(owner, observer)
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<UIState>) {
        communication.observeState(owner, observer)
    }

    interface  DispatchersList {

        fun io(): CoroutineDispatcher
        fun ui(): CoroutineDispatcher

        class Base @Inject constructor() : DispatchersList {
            override fun io(): CoroutineDispatcher {
                return Dispatchers.IO
            }

            override fun ui(): CoroutineDispatcher {
                return Dispatchers.Main
            }
        }
    }
}