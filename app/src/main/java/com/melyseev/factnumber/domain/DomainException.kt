package com.melyseev.factnumber.domain

sealed class DomainException: IllegalStateException() {
    object NoInternetException: DomainException()
    object UnknownErrorException: DomainException()
}