package com.fahrul.rackmovies.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.model.MovieList
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.model.TVList
import com.fahrul.rackmovies.model.lokal.FavoriteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataViewModel(context: Context) : ViewModel() {
    companion object {
        private val TAG = MovieDataViewModel::class.java.simpleName
    }

    private val apiService = ApiClient.create()
    private val apiKey = ApiClient.API_KEY

    private val favoriteDatabase = FavoriteDatabase.getInstance(context)

    private val movieList = MutableLiveData<ArrayList<Movie>>()
    private val tvShowList = MutableLiveData<ArrayList<TV>>()
    private val favoriteMovieList = MutableLiveData<ArrayList<Movie>>()
    private val favoriteTvShowList = MutableLiveData<ArrayList<TV>>()


    var isLoading = MutableLiveData<Boolean>()

    internal fun setMovies(queryTitle: String?) {
        isLoading.postValue(true)

        val getApiService: Call<MovieList> = if (queryTitle != null) {
            apiService.getMovie(apiKey, queryTitle)
        } else {
            apiService.getMovie(apiKey)
        }

        getApiService.enqueue(object : Callback<MovieList> {
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                isLoading.postValue(false)
            }

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    val list = response.body() as MovieList
                    movieList.postValue(list.results)
                }
            }
        })
    }

    internal fun setTvShow(queryTitle: String?) {
        isLoading.postValue(true)

        val getApiService: Call<TVList> = if (queryTitle != null) {
            apiService.getTvShow(apiKey, queryTitle)
        } else {
            apiService.getTVShow(apiKey)
        }

        getApiService.enqueue(object : Callback<TVList> {
            override fun onFailure(call: Call<TVList>, t: Throwable) {
                isLoading.postValue(false)
            }

            override fun onResponse(call: Call<TVList>, response: Response<TVList>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)

                    val list = response.body() as TVList

                    tvShowList.postValue(list.results)
                }
            }
        })
    }

    internal fun getMovies(init: Boolean): MutableLiveData<ArrayList<Movie>> {
        if (movieList.value == null && init) {
            setMovies(null)
        }
        return movieList
    }

    internal fun getTvShow(init: Boolean): MutableLiveData<ArrayList<TV>> {
        if (tvShowList.value == null && init) {
            setTvShow(null)
        }
        return tvShowList
    }

    internal fun getFavoriteMovieList(): MutableLiveData<ArrayList<Movie>> {
        GlobalScope.launch {
            val list = favoriteDatabase?.movieDao()?.getMovies()

            favoriteMovieList.postValue(list as ArrayList<Movie>?)
        }

        return favoriteMovieList
    }

    internal fun getFavoriteTvShowList(): MutableLiveData<ArrayList<TV>> {
        GlobalScope.launch {
            val list = favoriteDatabase?.tvShowDao()?.getTvShow()

            favoriteTvShowList.postValue(list as ArrayList<TV>?)
        }

        return favoriteTvShowList
    }
}