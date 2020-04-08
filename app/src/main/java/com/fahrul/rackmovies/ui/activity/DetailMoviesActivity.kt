package com.fahrul.rackmovies.ui.activity


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_movies.*

class DetailMoviesActivity : AppCompatActivity() {
    companion object {
        val EXTRA_ID_STRING = "extra_id_string"
    }

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var movieModel: Movie
    private var filmId = ""
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movies)

        if (intent.getStringExtra(EXTRA_ID_STRING) != null) {
            filmId = intent.getStringExtra(EXTRA_ID_STRING)!!
        }

        movieDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory()
                .viewModelFactory { MovieDetailViewModel(this, filmId) }).get(
            MovieDetailViewModel::class.java
        )

        movieDetailViewModel.getMovieDetail().observe(this, Observer { filmModel ->
            this.movieModel = filmModel
            actionBar?.let {
                it.title = movieModel.title
                it.setDisplayHomeAsUpEnabled(true)
            }

            Glide.with(this)
                .load(Helper.POSTER_URL + movieModel.poster_path)
                .into(img_poster)

            tvName.text = movieModel.title
            tvDate.text = movieModel.release_date
            tvDesc.text = movieModel.overview
        })
        checkFavorite(filmId)

        btnFav.setOnClickListener {
            if (isFavorite) {
                movieDetailViewModel.deleteFavoriteMovie(movieModel.id)
                showMessage(getString(R.string.removed_from_favorite))
            } else {
                movieDetailViewModel.setFavoriteMovie(movieModel)
                showMessage(getString(R.string.added_to_favorite))
            }
            checkFavorite(movieModel.id)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(viewParent, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun checkFavorite(id: String) {
        movieDetailViewModel.checkIsFavoriteMovie(id).observe(this, Observer { isFavorite ->
            this.isFavorite = if (isFavorite) {
                btnFav.setImageResource(R.drawable.ic_favorite_red_24dp)
                true
            } else {
                btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
