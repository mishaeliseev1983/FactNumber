package com.melyseev.factnumber.data

import com.melyseev.factnumber.domain.DomainException
import com.melyseev.factnumber.domain.HandleError
import java.net.UnknownHostException
import javax.inject.Inject

class HandleErrorToDomainException @Inject constructor(): HandleError<DomainException> {
    override fun handle(exception: Exception): DomainException =
        when (exception){
            is UnknownHostException -> DomainException.NoInternetException
            else -> DomainException.UnknownErrorException
        }
}