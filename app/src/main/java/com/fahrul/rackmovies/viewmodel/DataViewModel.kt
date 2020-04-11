package com.fahrul.rackmovies.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.lokal.FavoriteDb
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.model.MovieList
import com.fahrul.rackmovies.model.TVList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel(context: Context) : ViewModel() {
    companion object {
        private val TAG = DataViewModel::class.java.simpleName
    }

    private val favDb = FavoriteDb.getInstance(context)
    private val service = ApiClient.create()
    private val apiKey = Helper.API_KEY

    private val dataMovie = MutableLiveData<ArrayList<Movie>>()
    private val favMovie = MutableLiveData<ArrayList<Movie>>()

    private val dataTV = MutableLiveData<ArrayList<TV>>()
    private val favTV = MutableLiveData<ArrayList<TV>>()

    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Boolean>()

    internal fun getDataMovies(init: Boolean): MutableLiveData<ArrayList<Movie>> {
        if (dataMovie.value == null && init) {
            setMovies(null)
        }
        return dataMovie
    }

    internal fun setMovies(queryTitle: String?) {
        isLoading.postValue(true)

        val getApiService: Call<MovieList> = if (queryTitle != null) {
            service.getMovieSearch(apiKey, queryTitle)
        } else {
            service.getMovie(apiKey)
        }

        getApiService.enqueue(object : Callback<MovieList> {
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                isLoading.postValue(false)
                isError.postValue(true)
            }

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    isError.postValue(false)
                    val list = response.body() as MovieList
                    dataMovie.postValue(list.results)
                }
            }
        })
    }

    internal fun getFavoriteMovie(): MutableLiveData<ArrayList<Movie>> {
        GlobalScope.launch {
            val list = favDb?.movieDao()?.getMovies()
            favMovie.postValue(list as ArrayList<Movie>?)
        }

        return favMovie
    }


    internal fun getDataTV(init: Boolean): MutableLiveData<ArrayList<TV>> {
        if (dataTV.value == null && init) {
            setTvShow(null)
        }
        return dataTV
    }

    internal fun setTvShow(queryTitle: String?) {
        isLoading.postValue(true)

        val getApiService: Call<TVList> = if (queryTitle != null) {
            service.getTvSearch(apiKey, queryTitle)
        } else {
            service.getTV(apiKey)
        }

        getApiService.enqueue(object : Callback<TVList> {
            override fun onFailure(call: Call<TVList>, t: Throwable) {
                isLoading.postValue(false)
            }
            override fun onResponse(call: Call<TVList>, response: Response<TVList>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    val list = response.body() as TVList
                    dataTV.postValue(list.results)
                }
            }
        })
    }

    internal fun getFavoriteTV(): MutableLiveData<ArrayList<TV>> {
        GlobalScope.launch {
            val list = favDb?.tvShowDao()?.getTvShow()
            favTV.postValue(list as ArrayList<TV>?)
        }
        return favTV
    }
}