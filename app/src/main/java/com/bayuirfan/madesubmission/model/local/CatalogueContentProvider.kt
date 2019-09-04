package com.bayuirfan.madesubmission.model.local

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.bayuirfan.madesubmission.utils.Constant.AUTHORITY
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE

class CatalogueContentProvider : ContentProvider() {
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var database: CatalogueDatabase? = null

    init {
        uriMatcher.addURI(AUTHORITY, MOVIE_TABLE, CODE_MOVIE)
        uriMatcher.addURI(AUTHORITY, TV_SHOW_TABLE, CODE_TV_SHOW)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (uriMatcher.match(uri)){
            CODE_MOVIE -> {
                database?.movieDao()?.loadAllFavoritesCursor()
            }

            CODE_TV_SHOW -> {
                database?.tvShowDao()?.loadAllFavoritesCursor()
            }
            else -> {
                throw IllegalArgumentException("Unknown Uri: $uri")
            }
        }
    }

    override fun onCreate(): Boolean {
        database = context?.let { CatalogueDatabase.getInstance(it) }
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    companion object {
        private const val CODE_MOVIE = 0x1
        private const val CODE_TV_SHOW = 0x2
    }
}