package com.example.androidtesttask.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "https://geek-jokes.sameerkumar.website/"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        val client = OkHttpClient.Builder().build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        }
        return retrofit
    }
}