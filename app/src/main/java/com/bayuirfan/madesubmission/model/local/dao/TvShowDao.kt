package com.bayuirfan.madesubmission.model.local.dao

import android.database.Cursor
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import io.reactivex.Flowable

@Dao
interface TvShowDao {
    @Query("SELECT * FROM $TV_SHOW_TABLE")
    fun loadAllFavorites(): Flowable<List<TvShowModel>>

    @Query("SELECT * FROM $TV_SHOW_TABLE")
    fun loadAllFavoritesForWidget(): List<TvShowModel>

    @Query("SELECT * FROM $TV_SHOW_TABLE")
    fun loadAllFavoritesCursor(): Cursor

    @Query("SELECT * FROM $TV_SHOW_TABLE WHERE $ID_DATA = :id")
    fun loadFavoritesById(id: Int) : Flowable<TvShowModel>

    @Insert(onConflict = REPLACE)
    fun insertIntoFavorites(tvShowModel: TvShowModel)

    @Delete
    fun removeFromFavorites(tvShowModel: TvShowModel)
}