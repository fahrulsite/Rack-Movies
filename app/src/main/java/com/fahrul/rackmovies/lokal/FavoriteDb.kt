package com.fahrul.rackmovies.lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fahrul.rackmovies.Helper

@Database(entities = [Movie::class, TV::class], version = 1, exportSchema = false)
abstract class FavoriteDb : RoomDatabase() {
    abstract fun movieDao(): DatabaseDAO.FavoriteMovie
    abstract fun tvShowDao(): DatabaseDAO.FavoriteTvShow

    companion object {
        private var INSTANCE: FavoriteDb? = null
        fun getInstance(context: Context): FavoriteDb? {
            if (INSTANCE == null) {
                synchronized(FavoriteDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                FavoriteDb::class.java,
                                Helper.DB_NAME
                            )
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}