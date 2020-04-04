package com.fahrul.rackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.model.Movie
import kotlinx.android.synthetic.main.item_recycler.view.*


class DataAdapter(private val context: Context?) :
    RecyclerView.Adapter<DataAdapter.CardViewViewHolder>() {
    private val movieList = ArrayList<Movie>()

    companion object {
        internal var clickListener: ClickListener? = null
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        DataAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(data: Movie, v: View)
    }

    fun setData(items: ArrayList<Movie>) {
        movieList.clear()
        movieList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class CardViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(film: Movie) {
            with(view) {
                Glide.with(context)
                    .load(ApiClient.POSTER_URL + film.poster_path)
                    .into(img_poster)

                tvName.text = film.title
                tvDate.text = film.release_date
                tvDesc.text = film.overview

                view.setOnClickListener {
                    clickListener?.onItemClick(film, it)
                }
            }
        }
    }
}