package com.fahrul.consumerrackmovies


import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahrul.consumerrackmovies.adapter.MovieAdapter
import com.fahrul.consumerrackmovies.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = MovieAdapter(context)

        if (getMovieList() != null) {
            rvAdapter.setData(getMovieList()!!)
        }

        rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    private fun getMovieList(): ArrayList<Movie>? {
        val cursor = context?.contentResolver?.query(
            DbContract.FavoriteMovieColumns.CONTENT_URI_MOVIE,
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

    private fun cursorToListMovie(cursor: Cursor): ArrayList<Movie> {
        val list = ArrayList<Movie>()

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

                list.add(Movie(id.toString(), title, posterPath, release, overview))
            }
        }

        return list
    }

}
