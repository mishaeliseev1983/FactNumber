package com.melyseev.factnumber.domain

import com.melyseev.factnumber.R
import com.melyseev.factnumber.presentation.ManageResources

interface HandleError<T> {
    fun handle(e: Exception): T

    class HandleToString(private val manageResources: ManageResources): HandleError<String> {
        override fun  handle(exception: Exception) =
            when (exception) {
                DomainException.NoInternetException -> manageResources.string(R.string.no_internet_connection)
                else -> manageResources.string(R.string.no_internet_connection)
        }
    }
}