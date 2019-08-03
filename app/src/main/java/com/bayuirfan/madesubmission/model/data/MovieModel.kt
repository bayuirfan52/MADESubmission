package com.bayuirfan.madesubmission.model.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieModel(
        @SerializedName("vote_count")
        val voteCount: String?,
        val id: Int,
        val title: String?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("original_title")
        val originalTitle: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        val overview: String?,
        @SerializedName("release_date")
        val releaseDate: String?
): Parcelable {
    private constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(voteCount)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(originalTitle)
        parcel.writeString(backdropPath)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }
}
