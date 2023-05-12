package com.example.androidtesttask.retrofit

import com.example.androidtesttask.model.JokeResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET("api")
    suspend fun jokeFetch(@QueryMap params: HashMap<String, String>): Response<JokeResponseModel>
}