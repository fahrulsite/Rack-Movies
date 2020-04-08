package com.fahrul.rackmovies.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.fahrul.rackmovies.Helper
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.lokal.Movie
import com.fahrul.rackmovies.lokal.FavoriteDb
import com.fahrul.rackmovies.ui.AppWidget
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StackRemoteViewsFactory(val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val favoriteDatabase = FavoriteDb.getInstance(context)
    private var movieList = mutableListOf<Movie>()


    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        Log.d("stackWidget", "data onchange")

        GlobalScope.launch {
            val list = favoriteDatabase?.movieDao()?.getMovies()

            if (list != null) {
                movieList.clear()
                movieList.addAll(list)
            }
        }

    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(
            context.packageName,
            R.layout.item_widget
        )

        val bitmap = Glide.with(context)
            .asBitmap()
            .load(Helper.POSTER_URL + movieList[position].poster_path)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imgView, bitmap)

        val intent = Intent().putExtra(AppWidget.EXTRA_ITEM, movieList[position].id)

        rv.setOnClickFillInIntent(R.id.imgView, intent)
        return rv
    }

    override fun getCount(): Int {
        return movieList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}