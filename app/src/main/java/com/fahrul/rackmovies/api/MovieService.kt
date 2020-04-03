package com.fahrul.rackmovies.api

import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.model.MovieList
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.model.TVList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    fun getMovie(@Query("api_key") api_key: String): Call<MovieList>

    @GET("discover/tv")
    fun getTVShow(@Query("api_key") api_key: String): Call<TVList>

    @GET("search/movie")
    fun getMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String?
    ): Call<MovieList>

    @GET("search/tv")
    fun getTvShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String?
    ): Call<TVList>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<Movie>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") movie_id: String,
        @Query("api_key") api_key: String
    ): Call<TV>

    @GET("discover/movie")
    fun getReleaseMovie(
        @Query("api_key") api_key: String,
        @Query("primary_release_date.gte") start_release_date: String,
        @Query("primary_release_date.lte") until_release_date: String
    ): Call<MovieList>

}