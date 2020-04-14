package com.fahrul.consumerrackmovies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TV(
    var id: String,
    var name: String?,
    var poster_path: String?,
    var first_air_date: String?,
    var overview: String?
): Parcelable