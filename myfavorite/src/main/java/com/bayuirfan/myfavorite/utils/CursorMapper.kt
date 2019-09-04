package com.bayuirfan.myfavorite.utils

import android.database.Cursor
import com.bayuirfan.myfavorite.model.*
import com.bayuirfan.myfavorite.utils.Constant.BACKDROP_PATH
import com.bayuirfan.myfavorite.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.myfavorite.utils.Constant.ID
import com.bayuirfan.myfavorite.utils.Constant.ID_DATA
import com.bayuirfan.myfavorite.utils.Constant.NAME
import com.bayuirfan.myfavorite.utils.Constant.OVERVIEW
import com.bayuirfan.myfavorite.utils.Constant.POSTER_PATH
import com.bayuirfan.myfavorite.utils.Constant.RELEASE_DATE
import com.bayuirfan.myfavorite.utils.Constant.TITLE
import com.bayuirfan.myfavorite.utils.Constant.VOTE_AVERAGE

object CursorMapper {

    fun mapMovieCursorToArrayList(cursor: Cursor): ArrayList<MovieModel>{
        val movieList = ArrayList<MovieModel>()

        while (cursor.moveToNext()){
            val model = MovieModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE))
            )

            movieList.add(model)
        }

        return movieList
    }

    fun mapTvShowCursorToArrayList(cursor: Cursor): ArrayList<TvShowModel>{
        val tvShowList = ArrayList<TvShowModel>()

        while (cursor.moveToNext()){
            val model = TvShowModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE))
            )

            tvShowList.add(model)
        }

        return tvShowList
    }
}