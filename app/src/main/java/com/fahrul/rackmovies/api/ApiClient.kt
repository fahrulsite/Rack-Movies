package com.fahrul.rackmovies.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val API_URL = "https://api.themoviedb.org/3/"
    const val POSTER_URL = "https://image.tmdb.org/t/p/w185/"
    const val API_KEY = "a3c18bc8397bb5414899836d47d1a83f"

    fun create(): MovieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
            .create(MovieService::class.java)
    }
}