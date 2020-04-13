package com.fahrul.rackmovies.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.viewmodel.MovieDetailViewModel
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import com.fahrul.rackmovies.widget.AppWidget
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_tv.*
import kotlinx.android.synthetic.main.activity_detail_tv.btnFav
import kotlinx.android.synthetic.main.activity_detail_tv.img_poster
import kotlinx.android.synthetic.main.activity_detail_tv.tvDate
import kotlinx.android.synthetic.main.activity_detail_tv.tvDesc
import kotlinx.android.synthetic.main.activity_detail_tv.tvName

class DetailTVActivity : AppCompatActivity() {
    companion object {
        val EXTRA_ID = "extra_id"
    }

    private lateinit var detailViewModel: MovieDetailViewModel
    private lateinit var tvModel: TV
    private var filmId = ""
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = " "

        if (intent.getStringExtra(EXTRA_ID) != null) {
            filmId = intent.getStringExtra(EXTRA_ID)!!
        }

        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory()
                .viewModelFactory { MovieDetailViewModel(this, filmId) }).get(
            MovieDetailViewModel::class.java
        )

        detailViewModel.getTvDetail().observe(this, Observer { model ->
            this.tvModel = model

            Glide.with(this)
                .load(Helper.POSTER_URL + tvModel.poster_path)
                .into(img_poster)
            Glide.with(this)
                .load(Helper.BACKDROP_URL + tvModel.backdrop_path)
                .centerCrop()
                .into(imgBackdrop)
            tvName.text = tvModel.name
            tvDate.text = tvModel.first_air_date
            tvDesc.text = tvModel.overview
            tvRate.text = tvModel.vote_average

        })
        checkFavorite(filmId)

        btnFav.setOnClickListener {
            sendWidget()
            if (isFavorite) {
                detailViewModel.deleteFavoriteTv(tvModel.id)
                showMessage(getString(R.string.removed_from_favorite))
            } else {
                detailViewModel.setFavoriteTv(tvModel)
                showMessage(getString(R.string.added_to_favorite))
            }
            checkFavorite(tvModel.id)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(tvLy, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun sendWidget(){
        val intent = Intent(this, AppWidget::class.java)
        intent.action = AppWidget.UPDATE_WIDGET
        this.sendBroadcast(intent)
    }

    private fun checkFavorite(id: String) {
        detailViewModel.checkIsFavoriteTv(id).observe(this, Observer { isFavorite ->
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