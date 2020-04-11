package com.fahrul.rackmovies.lokal

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fahrul.rackmovies.Helper
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Helper.TABLE_MOVIE)
data class Movie(
    @PrimaryKey
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val title: String?,

    @ColumnInfo
    val poster_path: String?,

    @ColumnInfo
    val release_date: String,

    @ColumnInfo
    val overview: String?,

    @ColumnInfo
    val vote_average: String?,

    @ColumnInfo
    val backdrop_path: String?

) : Parcelable