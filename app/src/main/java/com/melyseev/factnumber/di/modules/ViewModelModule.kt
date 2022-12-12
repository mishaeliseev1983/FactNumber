package com.melyseev.factnumber.di.modules

import androidx.lifecycle.ViewModel
import com.melyseev.factnumber.di.ApplicationScope
import com.melyseev.factnumber.di.ViewModelKey
import com.melyseev.factnumber.presentation.EnterNumberViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(EnterNumberViewModel::class)
    @Binds
    fun bindEnterNumberViewModel(impl: EnterNumberViewModel): ViewModel
}