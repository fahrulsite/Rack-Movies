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
import com.fahrul.rackmovies.adapter.FavoriteMovieAdapter
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.ui.activity.DetailMoviesActivity
import com.fahrul.rackmovies.viewmodel.DataViewModel
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movies.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesFragment : Fragment() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var rvAdapter: FavoriteMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel = ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { DataViewModel(context!!) }).get(
            DataViewModel::class.java
        )

        rvAdapter = FavoriteMovieAdapter(context)
        setList()

    }

    override fun onResume() {
        super.onResume()
        dataViewModel.getFavoriteMovie().observe(this, Observer { list ->
            if (list != null) {
                if (list.isNotEmpty()) tvEmpty.visibility = View.GONE
                rvAdapter.setData(list)
            }
        })
    }

    private fun setList() {
        rvAdapter.setOnItemClickListener(object : FavoriteMovieAdapter.ClickListener {
            override fun onItemClick(data: Movie, v: View) {
                val intent = Intent(context, DetailMoviesActivity::class.java)
                intent.putExtra(DetailMoviesActivity.EXTRA_ID_STRING, data.id)

                startActivity(intent)
            }
        })

        rvFavorit.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }
}
