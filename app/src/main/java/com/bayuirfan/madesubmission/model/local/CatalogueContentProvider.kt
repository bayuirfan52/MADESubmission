package com.bayuirfan.madesubmission.model.local

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.utils.Constant.AUTHORITY
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import com.bayuirfan.madesubmission.utils.Constant.NAME
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_DATE
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE


class CatalogueContentProvider : ContentProvider(){
    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private fun initializeUriMather(){
        uriMatcher.addURI(AUTHORITY, MOVIE_TABLE, MOVIE)
        uriMatcher.addURI(AUTHORITY, TV_SHOW_TABLE, TV_SHOW)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var cursor: Cursor? = null

        when (uriMatcher.match(uri)){
            MOVIE -> {
                cursor = queryLoadFavoriteMovies()
            }
            TV_SHOW -> {
                cursor = queryLoadFavoriteTvShow()
            }
        }

        return cursor
    }

    override fun onCreate(): Boolean {
        initializeUriMather()
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    private fun queryLoadFavoriteMovies(): Cursor? =
            context?.database?.writableDatabase?.query(
                MOVIE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$ID ASC")


    private fun queryLoadFavoriteTvShow(): Cursor? =
            context?.database?.writableDatabase?.query(
                    TV_SHOW_TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "$ID ASC"
            )

    companion object {
        private const val MOVIE = 1
        private const val TV_SHOW = 2

        fun mapMovieCursorToArrayList(cursor: Cursor): ArrayList<MovieModel>{
            val list = ArrayList<MovieModel>()

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

                list.add(model)
            }

            return list
        }

        fun mapTvShowCursorToArrayList(cursor: Cursor): ArrayList<TvShowModel>{
            val list = ArrayList<TvShowModel>()

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

                list.add(model)
            }

            return list
        }
    }
}