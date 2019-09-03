package com.bayuirfan.madesubmission.model.local

import android.arch.persistence.room.*
import android.content.Context
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.model.local.dao.*
import com.bayuirfan.madesubmission.utils.Constant.DATABASE_NAME
import com.bayuirfan.madesubmission.utils.Constant.DATABASE_VERSION

@Database(version = DATABASE_VERSION, entities = [MovieModel::class, TvShowModel::class])
abstract class CatalogueDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    companion object {
        private var INSTANCE : CatalogueDatabase? = null

        fun getInstance(context: Context): CatalogueDatabase? {
            if (INSTANCE == null){
                synchronized(CatalogueDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CatalogueDatabase::class.java, DATABASE_NAME)
                            .build()
                }
            }

            return INSTANCE
        }
    }
}