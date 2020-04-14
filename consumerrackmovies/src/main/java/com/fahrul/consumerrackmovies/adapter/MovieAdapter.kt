package com.fahrul.consumerrackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.consumerrackmovies.R
import com.fahrul.consumerrackmovies.model.Movie
import kotlinx.android.synthetic.main.item_recycler.view.*

class MovieAdapter(private val context: Context?) :
    RecyclerView.Adapter<MovieAdapter.FavViewHolder>() {
    private val movieList = ArrayList<Movie>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return FavViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun setData(items: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(items)
        notifyDataSetChanged()
    }

    class FavViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            with(view) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185/" + movie.poster_path)
                    .into(img_poster)
                tvName.text = movie.title
                tvDesc.text = movie.overview
                tvDate.text = movie.release_date
            }
        }
    }

}