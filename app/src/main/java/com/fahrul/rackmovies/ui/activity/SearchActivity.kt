package com.fahrul.rackmovies.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.DataAdapter
import com.fahrul.rackmovies.adapter.TVAdapter
import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.util.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var movieDataViewModel: MovieDataViewModel
    private var type: String?= null
    private var query: String? = null
    private var loadData: Unit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        movieDataViewModel= ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { MovieDataViewModel(this) }).get(
            MovieDataViewModel::class.java
        )

        query = intent?.getStringExtra(MainActivity.EXTRA_QUERY)
        type = intent?.getStringExtra(MainActivity.EXTRA_TYPE)

        supportActionBar?.title = "$type : $query"

        if (type == MainActivity.TYPE_MOVIE) {
            loadData = movieDataViewModel.setMovies(query)
            setMovieList()
        } else {
            loadData = movieDataViewModel.setTvShow(query)
            setTvShowList()
        }

        setSwipeRefresh()
    }

    private fun setTvShowList() {
        val rvAdapter = TVAdapter(this)

        rvAdapter.setOnClickListener(object : TVAdapter.ClickListener {
            override fun onItemClick(data: TV, view: View) {
                val intent = Intent(this@SearchActivity, DetailTVActivity::class.java)
                intent.putExtra(DetailTVActivity.EXTRA_ID, data.id)

                startActivity(intent)
            }
        })

        rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        movieDataViewModel.getTvShow(false).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
                isNotFound(false)
            }else{
                isNotFound(true)
            }
        })
    }

    private fun setMovieList() {
        val rvAdapter = DataAdapter(this)

        rvAdapter.setOnItemClickListener(object : DataAdapter.ClickListener {
            override fun onItemClick(data: Movie, v: View) {
                val intent = Intent(this@SearchActivity, DetailMoviesActivity::class.java)
                intent.putExtra(DetailMoviesActivity.EXTRA_ID_STRING, data.id)

                startActivity(intent)
            }
        })

        rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        movieDataViewModel.getMovies(false).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
                isNotFound(false)
            } else {
                isNotFound(true)
            }
        })
    }

    private fun isNotFound(boolean: Boolean){
        if (boolean){
            rvSearch.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        }else{
            rvSearch.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
        }
    }

    private fun setSwipeRefresh() {
        movieDataViewModel.isLoading.observe(this, Observer { loading ->
            swipeRefresh.isRefreshing = loading
        })

        swipeRefresh.setOnRefreshListener {
            loadData
        }
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
