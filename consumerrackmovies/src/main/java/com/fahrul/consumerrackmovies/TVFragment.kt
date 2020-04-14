package com.fahrul.consumerrackmovies


import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.consumerrackmovies.adapter.TVAdapter
import com.fahrul.consumerrackmovies.model.TV
import kotlinx.android.synthetic.main.fragment_tv.*

/**
 * A simple [Fragment] subclass.
 */
class TVFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = TVAdapter(context)

        if (getMovieList() != null) {
            rvAdapter.setData(getMovieList()!!)
        }

        rvTV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    private fun getMovieList(): ArrayList<TV>? {
        val cursor = context?.contentResolver?.query(
            DbContract.FavoriteTVColumns.CONTENT_URI_TV,
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

    private fun cursorToListMovie(cursor: Cursor): ArrayList<TV> {
        val list = ArrayList<TV>()

        while (cursor.moveToNext()) {
            DbContract.FavoriteMovieColumns.let {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.FavoriteTVColumns.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteTVColumns.NAME))
                val posterPath =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteTVColumns.POSTER_PATH))
                val release =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteTVColumns.FIRST_AIR_DATE))
                val overview =
                    cursor.getString(cursor.getColumnIndexOrThrow(DbContract.FavoriteTVColumns.OVERVIEW))

                list.add(TV(id.toString(), title, posterPath, release, overview))
            }
        }

        return list
    }


}
