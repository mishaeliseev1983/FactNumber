package com.melyseev.factnumber.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


interface ObserveCommunication {
    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observeNumberList(owner: LifecycleOwner, observer: Observer<List<NumberUI>>)
    fun observeState(owner: LifecycleOwner, observer: Observer<UIState>)
}