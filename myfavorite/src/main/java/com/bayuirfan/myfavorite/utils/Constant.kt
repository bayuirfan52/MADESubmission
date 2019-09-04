package com.bayuirfan.myfavorite.utils

import android.net.Uri

object Constant {
    const val IMG_W185 = "w185"
    private const val AUTHORITY = "com.bayuirfan.madesubmission"

    // Tables
    private const val MOVIE_TABLE = "MOVIE"
    private const val TV_SHOW_TABLE = "TV_SHOW"
    private const val SCHEME = "content"
    val MOVIE_CONTENT_URI: Uri
        get() = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MOVIE_TABLE)
                .build()

    val TV_SHOW_CONTENT_URI: Uri
        get() = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TV_SHOW_TABLE)
                .build()

    // Columns
    const val TITLE = "TITLE"
    const val NAME = "NAME"
    const val POSTER_PATH = "POSTER_PATH"
    const val ID = "_id"
    const val VOTE_AVERAGE = "VOTE_AVERAGE"
    const val BACKDROP_PATH = "BACKDROP_PATH"
    const val OVERVIEW = "OVERVIEW"
    const val RELEASE_DATE = "RELEASE_DATE"
    const val FIRST_AIR_DATE = "FIRST_AIR_DATE"
    const val ID_DATA = "ID"
}