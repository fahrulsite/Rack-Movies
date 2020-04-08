package com.fahrul.rackmovies.lokal

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fahrul.rackmovies.Helper
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Helper.TABLE_TV_SHOW)
data class TV(
    @PrimaryKey
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val name: String?,

    @ColumnInfo
    val poster_path: String?,

    @ColumnInfo
    val first_air_date: String?,

    @ColumnInfo
    val overview: String?
) : Parcelable