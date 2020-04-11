package com.fahrul.rackmovies.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.DataMovieAdapter
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.ui.activity.DetailMoviesActivity
import com.fahrul.rackmovies.viewmodel.DataViewModel
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies.*


/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {
    private lateinit var dataViewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { DataViewModel(context!!) }).get(
            DataViewModel::class.java
        )

        setList()
        search()
        setSwipeRefresh()
    }

    private fun search(){
        btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty() ){
                    setList()
                } else{
                    dataViewModel.setMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?):Boolean{
                if (query.isNullOrEmpty()){
                    setList()
                } else{
                    dataViewModel.setMovies(query)
                }
                return true
            }
        })
    }

    private fun setList() {
        val rvAdapter = DataMovieAdapter(context)

        rvAdapter.setOnItemClickListener(object : DataMovieAdapter.ClickListener {
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

        dataViewModel.getDataMovies(true).observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                rvAdapter.setData(list)
                isError(false)
            } else{
                isError(true)
                Toast.makeText(context, getString(R.string.notfound), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSwipeRefresh() {
        dataViewModel.isLoading.observe(this, Observer { loading ->
            swipeRefresh.isRefreshing = loading
        })

        swipeRefresh.setOnRefreshListener {
            dataViewModel.setMovies(null)
        }
    }

    private fun isError(boolean: Boolean) {
        if (boolean) {
            rvMovie.visibility = View.GONE
        } else {
            rvMovie.visibility = View.VISIBLE
        }
    }
}