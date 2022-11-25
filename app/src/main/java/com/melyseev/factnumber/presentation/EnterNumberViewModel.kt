package com.melyseev.factnumber.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melyseev.factnumber.R
import com.melyseev.factnumber.domain.NumberInteractor
import com.melyseev.factnumber.domain.NumberResultMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnterNumberViewModel(
    private val dispatchersList: DispatchersList = DispatchersList.Base(),
    private val communication: NumberCommunication,
    private val interactor: NumberInteractor,
    private val numberResultMapper: NumberResultMapper,
    private val manageResources: ManageResources
) : ViewModel(), FetchNumber, ObserveCommunication {


   override fun init(isFirstTime: Boolean) {
        if (isFirstTime) {
            communication.showProgress(true)
            viewModelScope.launch(dispatchersList.io()) {
                val numberResult = interactor.init(isFirstInit = true)
                numberResult.map(numberResultMapper)
                communication.showProgress(false)
            }
        }
    }

    override fun fetchAboutNumber(number: String) {
        if(number.isEmpty()){
            communication.showState(UIState.Failure(manageResources.string(R.string.enter_number)))
        }else{

            communication.showProgress(true)
            viewModelScope.launch(dispatchersList.io()) {
                val res = interactor.fetchAboutNumber("1")
                res.map(numberResultMapper)
                communication.showProgress(false)
            }
        }
    }

    override fun fetchRandomNumber() {
        communication.showProgress(true)
        viewModelScope.launch(dispatchersList.io()) {
            val res = interactor.fetchAboutRandom()
            res.map(numberResultMapper)
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

    class Base : DispatchersList {
        override fun io(): CoroutineDispatcher {
            return Dispatchers.IO
        }

        override fun ui(): CoroutineDispatcher {
            return Dispatchers.Main
        }
    }
}
}