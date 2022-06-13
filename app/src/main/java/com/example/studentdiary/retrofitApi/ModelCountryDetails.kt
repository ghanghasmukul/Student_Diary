package com.example.studentdiary.retrofitApi

data class ModelCountryDetails(
    val `data`: List<CountryData>,
    val error: Boolean,
    val msg: String
)