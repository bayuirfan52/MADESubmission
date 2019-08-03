package com.bayuirfan.madesubmission.model.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TvShowModel(
        @SerializedName("original_name")
        val originalName: String?,
        val name: String?,
        @SerializedName("vote_count")
        val voteCount: Int,
        @SerializedName("first_air_date")
        val firstAirDate: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val id: Int,
        val overview: String?,
        @SerializedName("poster_path")
        val posterPath: String?
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
        parcel.writeString(originalName)
        parcel.writeString(name)
        parcel.writeInt(voteCount)
        parcel.writeString(firstAirDate)
        parcel.writeString(backdropPath)
        parcel.writeInt(id)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
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