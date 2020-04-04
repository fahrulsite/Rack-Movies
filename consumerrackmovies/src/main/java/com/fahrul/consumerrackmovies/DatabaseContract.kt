package com.fahrul.consumerrackmovies

import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {
    internal class FavoriteMovieColumns : BaseColumns {
        companion object {
            private const val AUTHORITY = "com.fahrul.rackmovies"
            private const val SCHEME = "content"

            private const val TABLE_NAME = "favorite_movie"
            const val ID = "id"
            const val TITLE = "title"
            const val POSTER_PATH = "poster_path"
            const val RELEASE_DATE = "release_date"
            const val OVERVIEW = "overview"

            val CONTENT_URI: Uri = Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}