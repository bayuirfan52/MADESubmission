package com.bayuirfan.madesubmission.model.data

import android.arch.persistence.room.*
import android.os.Parcelable
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_DATE
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = MOVIE_TABLE)
data class MovieModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ID)
        val ids: Int,

        @SerializedName("id")
        @ColumnInfo(name = ID_DATA)
        val idData: Int,

        @ColumnInfo(name = TITLE)
        val title: String?,

        @SerializedName("poster_path")
        @ColumnInfo(name = POSTER_PATH)
        val posterPath: String?,

        @SerializedName("backdrop_path")
        @ColumnInfo(name = BACKDROP_PATH)
        val backdropPath: String?,

        @ColumnInfo(name = OVERVIEW)
        val overview: String?,

        @SerializedName("vote_average")
        @ColumnInfo(name = VOTE_AVERAGE)
        val voteAverage: String?,

        @ColumnInfo(name = RELEASE_DATE)
        @SerializedName("release_date")
        val releaseDate: String?
): Parcelable
