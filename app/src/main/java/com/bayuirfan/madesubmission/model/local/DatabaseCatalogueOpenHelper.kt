package com.bayuirfan.madesubmission.model.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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
import org.jetbrains.anko.db.*

class DatabaseCatalogueOpenHelper(context: Context): ManagedSQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private var instance: DatabaseCatalogueOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseCatalogueOpenHelper{
            if (instance == null){
                instance = DatabaseCatalogueOpenHelper(context.applicationContext)
            }
            return instance as DatabaseCatalogueOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(MOVIE_TABLE, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ID_DATA to INTEGER + UNIQUE,
                TITLE to TEXT,
                POSTER_PATH to TEXT,
                BACKDROP_PATH to TEXT,
                VOTE_AVERAGE to TEXT,
                OVERVIEW to TEXT,
                RELEASE_DATE to TEXT)

        db?.createTable(TV_SHOW_TABLE, true,
                ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ID_DATA to INTEGER + UNIQUE,
                NAME to TEXT,
                POSTER_PATH to TEXT,
                BACKDROP_PATH to TEXT,
                VOTE_AVERAGE to TEXT,
                OVERVIEW to TEXT,
                FIRST_AIR_DATE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(MOVIE_TABLE, true)
        db?.dropTable(TV_SHOW_TABLE, true)
    }
}

val Context.database: DatabaseCatalogueOpenHelper
    get() = DatabaseCatalogueOpenHelper.getInstance(applicationContext)