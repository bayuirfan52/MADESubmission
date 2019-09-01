package com.bayuirfan.madesubmission.model.local

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.bayuirfan.madesubmission.utils.Constant.AUTHORITY
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import com.bayuirfan.madesubmission.utils.Constant.SCHEME
import org.jetbrains.anko.db.select


class CatalogueContentProvider : ContentProvider(){
    private lateinit var cursor: Cursor

    private val CATALOGUE_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE)
            .build()

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun onCreate(): Boolean = true

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    private fun queryLoadFavoriteMovies(): Cursor {
        context?.database?.use {
            select(MOVIE_TABLE)
                    .exec {
                        cursor = this
                    }
        }

        return cursor
    }
}