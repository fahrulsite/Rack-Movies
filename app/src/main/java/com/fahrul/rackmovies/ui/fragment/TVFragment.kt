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
import com.fahrul.rackmovies.adapter.TVAdapter
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.ui.activity.DetailTVActivity
import com.fahrul.rackmovies.util.ViewModelFactory
import com.fahrul.rackmovies.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.fragment_tv.*


/**
 * A simple [Fragment] subclass.
 */
class TVFragment : Fragment() {
    private lateinit var movieDataViewModel: MovieDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
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
        val rvAdapter = TVAdapter(context)

        rvAdapter.setOnClickListener(object : TVAdapter.ClickListener {
            override fun onItemClick(data: TV, v: View) {
                val intent = Intent(context, DetailTVActivity::class.java)
                intent.putExtra(DetailTVActivity.EXTRA_ID, data.id)

                startActivity(intent)
            }

        })

        rvTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        movieDataViewModel.getTvShow(true).observe(this, Observer { list ->
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
            movieDataViewModel.setTvShow(null)
        }
    }


}
