package com.fahrul.rackmovies.api

import com.fahrul.rackmovies.Helper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
        fun create(): MovieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Helper.API_URL)
            .build()
            .create(MovieService::class.java)
    }
}