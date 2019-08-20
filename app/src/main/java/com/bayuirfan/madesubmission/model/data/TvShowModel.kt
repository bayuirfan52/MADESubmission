package com.bayuirfan.madesubmission.model.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TvShowModel(
        val ids: Int,
        @SerializedName("id")
        val idData: Int,
        val name: String?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val overview: String?,
        @SerializedName("vote_average")
        val voteAverage: String?,
        @SerializedName("first_air_date")
        val firstAirDate: String?
): Parcelable {
    private constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ids)
        parcel.writeInt(idData)
        parcel.writeString(name)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(overview)
        parcel.writeString(voteAverage)
        parcel.writeString(firstAirDate)
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