package com.fahrul.rackmovies.model

import com.fahrul.rackmovies.lokal.TV


data class TVList(
    var page: String?,
    var total_results: String?,
    var total_pages: String?,
    var results: ArrayList<TV>
)