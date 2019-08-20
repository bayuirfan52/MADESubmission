package com.bayuirfan.madesubmission.model.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.utils.CatalogueOpenHelperInterface
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE

class TvShowOpenHelper(context: Context): CatalogueOpenHelperInterface {
    private val databaseHelper = DatabaseCatalogueOpenHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var instance : TvShowOpenHelper? = null
        fun getInstance(context: Context): TvShowOpenHelper{
            if (instance == null){
                instance = TvShowOpenHelper(context)
            }

            return instance as TvShowOpenHelper
        }
    }

    override fun open() {
        database = databaseHelper.writableDatabase
    }

    override fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    @SuppressLint("Recycle")
    fun getAllTvShow(): ArrayList<TvShowModel> {
        val list = ArrayList<TvShowModel>()
        val cursor = database.query(TV_SHOW_TABLE, null,
                null,
                null,
                null,
                null,
                "$ID ASC",
                null)

        cursor.moveToFirst()
        var tvShow: TvShowModel
        if (cursor.count > 0){
            do {
                tvShow = TvShowModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE))
                )

                list.add(tvShow)
            }while (!cursor.isAfterLast)
        }

        cursor.close()
        return list
    }

    @SuppressLint("Recycle")
    fun getTvShowById(id: Int):ArrayList<TvShowModel>{
        val list = ArrayList<TvShowModel>()
        val cursor = database.query(TV_SHOW_TABLE, null,
                "$ID_DATA = ?",
                arrayOf(id.toString()),
                null,
                null,
                "$ID ASC",
                null)

        cursor.moveToFirst()
        var tvShow: TvShowModel
        if (cursor.count > 0){
            do {
                tvShow = TvShowModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE))
                )

                list.add(tvShow)
            }while (!cursor.isAfterLast)
        }

        cursor.close()
        return list
    }

    fun insertToFavorite(tvShow: TvShowModel) : Long{
        val args = ContentValues()
        args.put(ID_DATA, tvShow.idData)
        args.put(TITLE, tvShow.name)
        args.put(POSTER_PATH, tvShow.posterPath)
        args.put(BACKDROP_PATH, tvShow.backdropPath)
        args.put(OVERVIEW, tvShow.overview)
        args.put(VOTE_AVERAGE, tvShow.voteAverage)
        args.put(FIRST_AIR_DATE, tvShow.firstAirDate)

        return database.insert(TV_SHOW_TABLE, null, args)
    }

    fun deleteFromFavorite(id: Int) =
            database.delete(TV_SHOW_TABLE, "$ID = '$id", null)
}