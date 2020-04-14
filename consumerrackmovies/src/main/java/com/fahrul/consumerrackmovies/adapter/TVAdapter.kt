package com.fahrul.consumerrackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.consumerrackmovies.R
import com.fahrul.consumerrackmovies.model.TV
import kotlinx.android.synthetic.main.item_recycler.view.*

class TVAdapter(private val context: Context?):
    RecyclerView.Adapter<TVAdapter.FavViewHolder>() {
        private val tvList = ArrayList<TV>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
            return FavViewHolder(view)
        }

        override fun getItemCount(): Int {
            return tvList.size
        }

        override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
            holder.bind(tvList[position])
        }

        fun setData(items: ArrayList<TV>) {
            tvList.clear()
            tvList.addAll(items)
            notifyDataSetChanged()
        }

        class FavViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(tv : TV) {
                with(view) {
                    Glide.with(context)
                        .load("https://image.tmdb.org/t/p/w185/" + tv.poster_path)
                        .into(img_poster)
                    tvName.text = tv.name
                    tvDesc.text = tv.overview
                    tvDate.text = tv.first_air_date
                }
            }
        }
}