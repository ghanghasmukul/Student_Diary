package com.example.studentdiary.retrofitApi

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("/api/v0.1/countries/codes")
    fun getCode(): Call<ModelCountryDetails>


    companion object {

        var BASE_URL = "https://countriesnow.space"

        fun create(): ApiInterface {

            val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}