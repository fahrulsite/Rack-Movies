package com.fahrul.rackmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.api.ApiClient
import com.fahrul.rackmovies.model.TV
import kotlinx.android.synthetic.main.item_recycler.view.*

class FavoriteTVAdapter(private val context: Context?) :
    RecyclerView.Adapter<FavoriteTVAdapter.FavoriteTVViewHolder>() {
    private val tvList = ArrayList<TV>()

    companion object {
        internal var clickListener: ClickListener? = null
    }

    fun setOnClickListener(clickListener: ClickListener) {
        FavoriteTVAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(data: TV, v: View)
    }

    fun setData(items: ArrayList<TV>) {
        tvList.clear()
        tvList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTVViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return FavoriteTVViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onBindViewHolder(holder: FavoriteTVViewHolder, position: Int) {
        holder.bind(tvList[position])
    }

    class FavoriteTVViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tv: TV) {
            with(view) {
                Glide.with(context)
                    .load(ApiClient.POSTER_URL + tv.poster_path)
                    .into(img_poster)
                tvName.text = tv.name
                tvDate.text = tv.first_air_date
                tvDesc.text = tv.overview
                view.setOnClickListener {
                    clickListener?.onItemClick(tv, it)
                }
            }
        }
    }
}