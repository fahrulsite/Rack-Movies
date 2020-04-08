package com.fahrul.rackmovies.lokal

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface DatabaseDAO {
    @Dao
    interface FavoriteMovie {
        @Query("SELECT * FROM favMovie")
        fun getMovies(): List<Movie>

        @Query("SELECT * FROM favMovie WHERE id = :id")
        fun getMovie(id: String): Movie

        @Query("SELECT * FROM favMovie")
        fun getMoviesCursor(): Cursor

        @Query("SELECT * FROM favMovie WHERE id = :id")
        fun getMoviesCursor(id: String): Cursor

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertMovie(movie: Movie)

        @Query("DELETE FROM favMovie WHERE id = :id")
        fun deleteMovie(id: String)
    }

    @Dao
    interface FavoriteTvShow {
        @Query("SELECT * FROM favTV")
        fun getTvShow(): List<TV>

        @Query("SELECT * FROM favTV WHERE id = :id")
        fun getTvShow(id: String): TV

        @Query("SELECT * FROM favTV")
        fun getTvShowCursor(): Cursor

        @Query("SELECT * FROM favTV WHERE id = :id")
        fun getTvShowCursor(id: String): Cursor

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertTvShow(tvShow: TV)

        @Query("DELETE FROM favTV WHERE id = :id")
        fun deleteTvShow(id: String)
    }
}