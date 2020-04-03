package com.fahrul.rackmovies.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.DataAdapter
import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.ui.activity.DetailMoviesActivity
import com.fahrul.rackmovies.util.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.fragment_movies.*


/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {
    private lateinit var movieDataViewModel: MovieDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDataViewModel = ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { MovieDataViewModel(context!!) }).get(
            MovieDataViewModel::class.java
        )

        setList()
        setSwipeRefresh()
    }

    private fun setList() {
        val rvAdapter = DataAdapter(context)

        rvAdapter.setOnItemClickListener(object : DataAdapter.ClickListener {
            override fun onItemClick(data: Movie, v: View) {
                val intent = Intent(context, DetailMoviesActivity::class.java)
                intent.putExtra(DetailMoviesActivity.EXTRA_ID_STRING, data.id)

                startActivity(intent)
            }
        })

        rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        movieDataViewModel.getMovies(true).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
            }
        })
    }

    private fun setSwipeRefresh() {
        movieDataViewModel.isLoading.observe(this, Observer { loading ->
            swipeRefresh.isRefreshing = loading
        })

        swipeRefresh.setOnRefreshListener {
            movieDataViewModel.setMovies(null)
        }
    }


}