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
import com.fahrul.rackmovies.adapter.FavoriteTVAdapter
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.ui.activity.DetailTVActivity
import com.fahrul.rackmovies.util.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.fragment_favorite_tv.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTvFragment : Fragment() {

    private lateinit var movieDataViewModel: MovieDataViewModel
    private lateinit var rvAdapter: FavoriteTVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

    override fun onResume() {
        super.onResume()
        movieDataViewModel.getFavoriteTvShowList().observe(this, Observer { list ->
            if (list != null) {
                if (list.isNotEmpty()) tvEmpty.visibility = View.GONE
                rvAdapter.setData(list)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDataViewModel = ViewModelProvider(
            this,
            ViewModelFactory().viewModelFactory { MovieDataViewModel(context!!) }).get(
            MovieDataViewModel::class.java
        )

        rvAdapter = FavoriteTVAdapter(context)
        setList()
    }

    private fun setList() {
        rvAdapter.setOnClickListener(object : FavoriteTVAdapter.ClickListener {
            override fun onItemClick(data: TV, v: View) {
                val intent = Intent(context, DetailTVActivity::class.java)
                intent.putExtra(DetailTVActivity.EXTRA_ID, data.id)

                startActivity(intent)
            }
        })

        rvFavorit.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }
}
