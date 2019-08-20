package com.bayuirfan.madesubmission.model.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.DATABASE_NAME
import com.bayuirfan.madesubmission.utils.Constant.DATABASE_VERSION
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

class DatabaseCatalogueOpenHelper(context: Context) : SQLiteOpenHelper(
        context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val SQL_CREATE_MOVIE = "CREATE TABLE $MOVIE_TABLE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ID_DATA INTEGER UNIQUE, " +
                "$TITLE TEXT NOT NULL, " +
                "$POSTER_PATH TEXT, " +
                "$BACKDROP_PATH TEXT, " +
                "$RELEASE_DATE TEXT, " +
                "$VOTE_AVERAGE TEXT, " +
                "$OVERVIEW TEXT" +
                ")"

        private const val SQL_CREATE_TV_SHOW = "CREATE TABLE $TV_SHOW_TABLE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ID_DATA INTEGER UNIQUE, " +
                "$NAME TEXT NOT NULL, " +
                "$POSTER_PATH TEXT, " +
                "$BACKDROP_PATH TEXT, " +
                "$FIRST_AIR_DATE TEXT, " +
                "$VOTE_AVERAGE TEXT, " +
                "$OVERVIEW TEXT" +
                ")"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_MOVIE)
        db?.execSQL(SQL_CREATE_TV_SHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("$DROP_TABLE $MOVIE_TABLE")
        db?.execSQL("$DROP_TABLE $TV_SHOW_TABLE")
    }
}