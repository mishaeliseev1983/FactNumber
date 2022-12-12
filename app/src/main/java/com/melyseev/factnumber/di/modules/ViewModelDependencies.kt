package com.melyseev.factnumber.di.modules

import com.melyseev.factnumber.data.NumberRepositoryBase
import com.melyseev.factnumber.di.ApplicationScope
import com.melyseev.factnumber.domain.NumberInteractor
import com.melyseev.factnumber.domain.NumberRepository
import com.melyseev.factnumber.presentation.*
import dagger.Binds
import dagger.Module

@Module
interface ViewModelDependencies {

    @ApplicationScope
    @Binds
    fun provideDispatchersList(param: EnterNumberViewModel.DispatchersList.Base): EnterNumberViewModel.DispatchersList

    @ApplicationScope
    @Binds
    fun provideNumberCommunication(param: NumberCommunication.Base): NumberCommunication

    @Binds
    fun provideProgressCommunication(param: ProgressCommunication.Base): ProgressCommunication

    @Binds
    fun provideNumberStateCommunication(param: NumberStateCommunication.Base): NumberStateCommunication

    @Binds
    fun provideNumbersListCommunication(param: NumbersListCommunication.Base): NumbersListCommunication

    @ApplicationScope
    @Binds
    fun provideInteractor(param: NumberInteractor.Base): NumberInteractor

    @ApplicationScope
    @Binds
    fun provideNumberRepository(param: NumberRepositoryBase): NumberRepository

    @ApplicationScope
    @Binds
    fun provideManageResources(param: ManageResources.Base): ManageResources
}