package com.bayuirfan.madesubmission.model.data

import android.os.Parcelable
import androidx.room.*
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.NAME
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TV_SHOW_TABLE)
data class TvShowModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ID)
        val ids: Int,

        @SerializedName("id")
        @ColumnInfo(name = ID_DATA)
        val idData: Int,

        @ColumnInfo(name = NAME)
        val name: String?,

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

        @SerializedName("first_air_date")
        @ColumnInfo(name = FIRST_AIR_DATE)
        val firstAirDate: String?
): Parcelable