package com.fahrul.rackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.lokal.Movie
import kotlinx.android.synthetic.main.item_recycler.view.*

class FavoriteMovieAdapter(private val context: Context?) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {
    private val movieList = ArrayList<Movie>()

    companion object {
        internal var clickListener: ClickListener? = null
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        FavoriteMovieAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(data: Movie, v: View)
    }

    fun setData(items: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return FavoriteMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class FavoriteMovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            with(view) {
                Glide.with(context)
                    .load(Helper.POSTER_URL + movie.poster_path)
                    .into(img_poster)
                tvName.text = movie.title
                tvDate.text = movie.release_date
                tvDesc.text = movie.overview
                view.setOnClickListener {
                    clickListener?.onItemClick(movie, it)
                }
            }
        }
    }
}