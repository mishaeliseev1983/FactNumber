package com.melyseev.factnumber.presentation

import android.app.Application
import com.melyseev.factnumber.di.DaggerApplicationComponent


class App: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}