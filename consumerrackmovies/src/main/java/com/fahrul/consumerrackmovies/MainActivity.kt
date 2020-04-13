package com.fahrul.consumerrackmovies

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.consumerrackmovies.adapter.Adapter
import com.fahrul.consumerrackmovies.model.Favorit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvAdapter = Adapter(this@MainActivity)

        if (getMovieList() != null) {
            rvAdapter.setData(getMovieList()!!)
        }

        rvConsumer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    private fun getMovieList(): ArrayList<Favorit>? {
        val cursor = contentResolver?.query(
            DbContract.FavoriteMovieColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val list = cursor?.let { cursorToListMovie(it) }

        Log.d("myProvider", "consumer list : ${list?.size}")

        cursor?.close()

        return list
    }

    private fun cursorToListMovie(cursor: Cursor): ArrayList<Favorit> {
        val list = ArrayList<Favorit>()

        while (cursor.moveToNext()) {
            DbContract.FavoriteMovieColumns.let {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.FavoriteMovieColumns.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteMovieColumns.TITLE))
                val posterPath =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteMovieColumns.POSTER_PATH))
                val release =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteMovieColumns.RELEASE_DATE))
                val overview =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteMovieColumns.OVERVIEW))

                list.add(Favorit(id.toString(), title, posterPath, release, overview))
            }
        }

        return list
    }
}
