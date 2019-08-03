package com.bayuirfan.madesubmission.model.data

import android.os.Parcel
import android.os.Parcelable

data class TvShowModel(
        val original_name: String?,
        val name: String?,
        val vote_count: Int,
        val first_air_date: String?,
        val backdrop_path: String?,
        val id: Int,
        val overview: String?,
        val poster_path: String?
): Parcelable {
    private constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(original_name)
        parcel.writeString(name)
        parcel.writeInt(vote_count)
        parcel.writeString(first_air_date)
        parcel.writeString(backdrop_path)
        parcel.writeInt(id)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvShowModel> {
        override fun createFromParcel(parcel: Parcel): TvShowModel {
            return TvShowModel(parcel)
        }

        override fun newArray(size: Int): Array<TvShowModel?> {
            return arrayOfNulls(size)
        }
    }
}