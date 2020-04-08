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
import com.fahrul.rackmovies.adapter.DataMovieAdapter
import com.fahrul.rackmovies.adapter.DataTVAdapter
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel
    private var type: String? = null
    private var query: String? = null
    private var loadData: Unit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        dataViewModel = ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { DataViewModel(this) }).get(
            DataViewModel::class.java
        )

        query = intent?.getStringExtra(MainActivity.EXTRA_QUERY)
        type = intent?.getStringExtra(MainActivity.EXTRA_TYPE)

        supportActionBar?.title = "$type : $query"

        if (type == MainActivity.TYPE_MOVIE) {
            loadData = dataViewModel.setMovies(query)
            setMovieList()
        } else {
            loadData = dataViewModel.setTvShow(query)
            setTvShowList()
        }

        setSwipeRefresh()
    }

    private fun setTvShowList() {
        val rvAdapter = DataTVAdapter(this)

        rvAdapter.setOnClickListener(object : DataTVAdapter.ClickListener {
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

        dataViewModel.getDataTV(false).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
                isNotFound(false)
            } else {
                isNotFound(true)
            }
        })
    }

    private fun setMovieList() {
        val rvAdapter = DataMovieAdapter(this)

        rvAdapter.setOnItemClickListener(object : DataMovieAdapter.ClickListener {
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

        dataViewModel.getDataMovies(false).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
                isNotFound(false)
            } else {
                isNotFound(true)
            }
        })
    }

    private fun isNotFound(boolean: Boolean) {
        if (boolean) {
            rvSearch.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            rvSearch.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
        }
    }

    private fun setSwipeRefresh() {
        dataViewModel.isLoading.observe(this, Observer { loading ->
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
