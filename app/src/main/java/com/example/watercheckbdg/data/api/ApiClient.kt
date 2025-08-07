package com.example.watercheckbdg.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://opendata.bandung.go.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
