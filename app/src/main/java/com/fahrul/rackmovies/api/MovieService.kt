package com.fahrul.rackmovies.api

import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.model.MovieList
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.model.TVList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    fun getMovie(@Query("api_key") api_key: String): Call<MovieList>

    @GET("search/movie")
    fun getMovieSearch(@Query("api_key") api_key: String, @Query("query") query: String?): Call<MovieList>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movie_id: String, @Query("api_key") api_key: String): Call<Movie>

    @GET("discover/movie")
    fun getReleaseMovie(@Query("api_key") api_key: String, @Query("primary_release_date.gte") start_release_date: String, @Query("primary_release_date.lte") until_release_date: String): Call<MovieList>

    @GET("discover/tv")
    fun getTV(@Query("api_key") api_key: String): Call<TVList>

    @GET("search/tv")
    fun getTvSearch(@Query("api_key") api_key: String, @Query("query") query: String?): Call<TVList>

    @GET("tv/{tv_id}")
    fun getTvDetail(@Path("tv_id") movie_id: String, @Query("api_key") api_key: String): Call<TV>



}