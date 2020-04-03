package com.fahrul.rackmovies.model.lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fahrul.rackmovies.util.Constants
import com.fahrul.rackmovies.model.Movie
import com.fahrul.rackmovies.model.TV

@Database(entities =  [Movie::class, TV::class], version = 1)
abstract  class FavoriteDatabase :RoomDatabase() {
    abstract fun movieDao(): DatabaseDAO.FavoriteMovie
    abstract fun tvShowDao(): DatabaseDAO.FavoriteTvShow

    companion object {
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                FavoriteDatabase::class.java,
                                Constants.DB_NAME)
                            .build()
                    }
                }
            }

            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }
}