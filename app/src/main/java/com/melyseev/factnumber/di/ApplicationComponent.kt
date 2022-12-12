package com.melyseev.factnumber.di

import android.content.Context
import com.melyseev.factnumber.di.modules.AppModule
import com.melyseev.factnumber.di.modules.RetrofitModule
import com.melyseev.factnumber.di.modules.ViewModelDependencies
import com.melyseev.factnumber.di.modules.ViewModelModule
import com.melyseev.factnumber.presentation.EnterNumberFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
            AppModule::class,
            ViewModelModule::class,
            ViewModelDependencies::class,
            DataBaseModule::class,
            RetrofitModule::class])
interface ApplicationComponent {

    fun inject(fragment: EnterNumberFragment)

    @Component.Factory
    interface AppCompFactory{
        fun create(
            @BindsInstance context: Context): ApplicationComponent
    }
}