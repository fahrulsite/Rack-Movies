package com.fahrul.rackmovies.model

import com.fahrul.rackmovies.lokal.Movie


data class MovieList(
    var page: String?,
    var total_results: String?,
    var total_pages: String?,
    var results: ArrayList<Movie>
)
