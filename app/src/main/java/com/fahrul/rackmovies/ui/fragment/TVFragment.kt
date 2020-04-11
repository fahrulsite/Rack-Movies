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
import com.fahrul.rackmovies.adapter.DataTVAdapter
import com.fahrul.rackmovies.lokal.TV
import com.fahrul.rackmovies.ui.activity.DetailTVActivity
import com.fahrul.rackmovies.viewmodel.DataViewModel
import com.fahrul.rackmovies.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.btnSearch
import kotlinx.android.synthetic.main.fragment_tv.swipeRefresh


/**
 * A simple [Fragment] subclass.
 */
class TVFragment : Fragment() {
    private lateinit var dataViewModel: DataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
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
                    dataViewModel.setTvShow(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?):Boolean{
                if (query.isNullOrEmpty()){
                    setList()
                } else{
                    dataViewModel.setTvShow(query)
                }
                return true
            }
        })
    }

    private fun setList() {
        val rvAdapter = DataTVAdapter(context)

        rvAdapter.setOnClickListener(object : DataTVAdapter.ClickListener {
            override fun onItemClick(data: TV, v: View) {
                val intent = Intent(context, DetailTVActivity::class.java)
                intent.putExtra(DetailTVActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })

        rvTV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        dataViewModel.getDataTV(true).observe(this, Observer { list ->
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
            dataViewModel.setTvShow(null)
        }
    }

    private fun isError(boolean: Boolean) {
        if (boolean) {
            rvTV.visibility = View.GONE
        } else {
            rvTV.visibility = View.VISIBLE
        }
    }

}
