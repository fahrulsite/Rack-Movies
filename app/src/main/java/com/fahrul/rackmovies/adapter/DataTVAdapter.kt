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
import com.fahrul.rackmovies.lokal.TV
import kotlinx.android.synthetic.main.item_recycler.view.*


class DataTVAdapter(private val context: Context?) :
    RecyclerView.Adapter<DataTVAdapter.CardViewViewHolder>() {
    private val data = ArrayList<TV>()

    fun setData(items: ArrayList<TV>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class CardViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(film: TV) {
            with(view) {
                Glide.with(context)
                    .load(Helper.POSTER_URL + film.poster_path)
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

    companion object {
        internal var clickListener: ClickListener? = null
    }

    fun setOnClickListener(clickListener: ClickListener) {
        DataTVAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(data: TV, v: View)
    }
}