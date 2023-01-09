package com.example.vodafonetask.util

sealed class Validation {
    object NameEmpty : Validation()
    object SloganEmpty: Validation()
    object CountryEmpty: Validation()
    object HeadquatersEmpty: Validation()
    object EstablishedEmpty: Validation()
}