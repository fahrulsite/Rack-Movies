package com.fahrul.rackmovies.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTVActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "extra_id"
    }

    private lateinit var tvDetailViewModel: MovieDetailViewModel
    private lateinit var tvModel: TV
    private var filmId = ""
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        if (intent.getStringExtra(EXTRA_ID) != null) {
            filmId = intent.getStringExtra(EXTRA_ID)!!
        }

        tvDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory()
                .viewModelFactory { MovieDetailViewModel(this, filmId) }).get(
            MovieDetailViewModel::class.java
        )

        tvDetailViewModel.getTvShowDetail().observe(this, Observer { filmModel ->
            this.tvModel = filmModel
            actionBar?.let {
                it.title = tvModel.name
                it.setDisplayHomeAsUpEnabled(true)
            }

            Glide.with(this)
                .load(Helper.POSTER_URL + tvModel.poster_path)
                .into(img_poster)

            tvName.text = tvModel.name
            tvDate.text = tvModel.first_air_date
            tvDesc.text = tvModel.overview
        })
        checkFavorite(filmId)

        btnFav.setOnClickListener {
            if (isFavorite) {
                tvDetailViewModel.deleteFavoriteTvShow(tvModel.id)
                showMessage(getString(R.string.removed_from_favorite))
            } else {
                tvDetailViewModel.setFavoriteTvShow(tvModel)
                showMessage(getString(R.string.added_to_favorite))
            }
            checkFavorite(tvModel.id)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(viewParent, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun checkFavorite(id: String) {
        tvDetailViewModel.checkIsFavoriteTvShow(id).observe(this, Observer { isFavorite ->
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
