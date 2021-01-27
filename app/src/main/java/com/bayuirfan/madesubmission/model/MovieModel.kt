package com.bayuirfan.madesubmission.model

import android.os.Parcel
import android.os.Parcelable

class MovieModel : Parcelable {
    var title: String? = null
    var description: String? = null
    var yearAired: String? = null
    var score: String? = null
    var genre: String? = null
    var duration: String? = null
    var poster = 0
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(yearAired)
        dest.writeString(score)
        dest.writeString(genre)
        dest.writeString(duration)
        dest.writeInt(poster)
    }

    constructor()
    private constructor(`in`: Parcel) {
        title = `in`.readString()
        description = `in`.readString()
        yearAired = `in`.readString()
        score = `in`.readString()
        genre = `in`.readString()
        duration = `in`.readString()
        poster = `in`.readInt()
    }

    companion object {
        val CREATOR: Parcelable.Creator<MovieModel?> = object : Parcelable.Creator<MovieModel?> {
            override fun createFromParcel(source: Parcel): MovieModel {
                return MovieModel(source)
            }

            override fun newArray(size: Int): Array<MovieModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}