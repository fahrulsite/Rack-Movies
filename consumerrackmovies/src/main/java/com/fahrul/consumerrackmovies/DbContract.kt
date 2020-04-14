package com.fahrul.consumerrackmovies

import android.net.Uri
import android.provider.BaseColumns

class DbContract {
    internal class FavoriteMovieColumns : BaseColumns {
        companion object {
            private const val AUTHORITY = "com.fahrul.rackmovies"
            private const val SCHEME = "content"

            private const val TABLE_MOVIE = "favMovie"
            const val ID = "id"
            const val TITLE = "title"
            const val POSTER_PATH = "poster_path"
            const val RELEASE_DATE = "release_date"
            const val OVERVIEW = "overview"


            val CONTENT_URI_MOVIE: Uri = Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build()
        }
    }

    internal class FavoriteTVColumns : BaseColumns {
        companion object {
            private const val AUTHORITY = "com.fahrul.rackmovies"
            private const val SCHEME = "content"
            private const val TABLE_TV = "favTV"
            const val ID = "id"
            const val NAME = "name"
            const val POSTER_PATH = "poster_path"
            const val FIRST_AIR_DATE = "first_air_date"
            const val OVERVIEW = "overview"


            val CONTENT_URI_TV: Uri = Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build()
        }
    }
}