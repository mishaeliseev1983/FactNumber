package com.melyseev.factnumber.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Change<Unit,S> {
    fun change(source: S) : Unit
}

interface Observe<T> {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
}

interface Mutable<T> : Observe<T>, Change<Unit, T>


abstract class AbstractMutable<T>(val liveData: MutableLiveData<T>) : Mutable<T>{
    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        liveData.observe(lifecycleOwner, observer)
    }
}

abstract class Post<T>(liveData: MutableLiveData<T> = MutableLiveData()) : AbstractMutable<T>(liveData){
    override fun change(source: T) {
        liveData.postValue(source)
    }
}


interface ProgressCommunication: Mutable<Boolean>{
    class Base: Post<Boolean>(), ProgressCommunication
}

interface NumberStateCommunication: Mutable<UIState>{
    class Base: Post<UIState>(), NumberStateCommunication
}

interface NumbersListCommunication: Mutable<List<NumberUI>>{
    class Base: Post<List<NumberUI>>(), NumbersListCommunication
}

