package com.fahrul.rackmovies.model.lokal

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.model.TV

interface DatabaseDAO {
    @Dao
    interface FavoriteMovie {
        @Query("SELECT * FROM favorite_movie")
        fun getMovies(): List<Movie>

        @Query("SELECT * FROM favorite_movie WHERE id = :id")
        fun getMovie(id: String): Movie

        @Query("SELECT * FROM favorite_movie")
        fun getMoviesCursor(): Cursor

        @Query("SELECT * FROM favorite_movie WHERE id = :id")
        fun getMoviesCursor(id: String): Cursor

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertMovie(movie: Movie)

        @Query("DELETE FROM favorite_movie WHERE id = :id")
        fun deleteMovie(id: String)
    }

    @Dao
    interface FavoriteTvShow {
        @Query("SELECT * FROM favorite_tv_show")
        fun getTvShow(): List<TV>

        @Query("SELECT * FROM favorite_tv_show WHERE id = :id")
        fun getTvShow(id: String): TV

        @Query("SELECT * FROM favorite_tv_show")
        fun getTvShowCursor(): Cursor

        @Query("SELECT * FROM favorite_tv_show WHERE id = :id")
        fun getTvShowCursor(id: String): Cursor

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertTvShow(tvShow: TV)

        @Query("DELETE FROM favorite_tv_show WHERE id = :id")
        fun deleteTvShow(id: String)
    }
}