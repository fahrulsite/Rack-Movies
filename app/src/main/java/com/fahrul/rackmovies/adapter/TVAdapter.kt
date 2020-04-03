package com.fahrul.rackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.model.TV
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.api.ApiClient
import kotlinx.android.synthetic.main.item_recycler.view.*


class TVAdapter(private val context: Context?) :
    RecyclerView.Adapter<TVAdapter.CardViewViewHolder>() {
    private val tvList = ArrayList<TV>()

    companion object {
        internal var clickListener: ClickListener? = null
    }

    fun setOnClickListener(clickListener: ClickListener) {
        TVAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(data: TV, v: View)
    }

    fun setData(items: ArrayList<TV>) {
        tvList.clear()
        tvList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = tvList.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(tvList[position])
    }

    class CardViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(film: TV) {
            with(view) {
                Glide.with(context)
                    .load(ApiClient.POSTER_URL + film.poster_path)
                    .into(img_poster)

                tvName.text = film.name
                tvDate.text = film.first_air_date
                tvDesc.text = film.overview

                view.setOnClickListener {
                    clickListener?.onItemClick(film, it)
                }
            }
        }
    }
}