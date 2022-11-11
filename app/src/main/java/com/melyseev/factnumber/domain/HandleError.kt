package com.melyseev.factnumber.domain

import com.melyseev.factnumber.R
import com.melyseev.factnumber.presentation.ManageResources

interface HandleError {
    fun handle(exception: DomainException): String

    class Base(val manageResources: ManageResources): HandleError{

        override fun handle(exception: DomainException): String =
            when(exception){
                DomainException.NoInternetException -> manageResources.string(R.string.no_internet_connection)
                else -> manageResources.string(R.string.no_internet_connection)
        }

    }
}