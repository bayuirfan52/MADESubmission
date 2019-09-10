package com.bayuirfan.madesubmission.model.local.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.database.Cursor
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM $MOVIE_TABLE")
    fun loadAllFavorites(): Flowable<List<MovieModel>>

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun loadAllFavoritesForWidget(): List<MovieModel>

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun loadAllFavoritesCursor(): Cursor

    @Query("SELECT * FROM $MOVIE_TABLE WHERE $ID_DATA = :id")
    fun loadFavoritesById(id: Int): Flowable<MovieModel>

    @Insert(onConflict = REPLACE)
    fun insertIntoFavorites(movieModel: MovieModel)

    @Delete
    fun removefromFavorites(movieModel: MovieModel)
}