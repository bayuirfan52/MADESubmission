package com.bayuirfan.madesubmission.model.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.utils.CatalogueOpenHelperInterface
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_DATE
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE

class MovieOpenHelper(context: Context): CatalogueOpenHelperInterface {
    private val databaseHelper = DatabaseCatalogueOpenHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private var instance : MovieOpenHelper? = null
        fun getInstance(context: Context): MovieOpenHelper{
            if (instance == null){
                instance = MovieOpenHelper(context)
            }

            return instance as MovieOpenHelper
        }
    }

    override fun open() {
        database = databaseHelper.writableDatabase
    }

    override fun close() {
        databaseHelper.close()
        if (database.isOpen){
            database.close()
        }
    }

    fun getAllMovies() : ArrayList<MovieModel>{
        val list = ArrayList<MovieModel>()
        @SuppressLint("Recycle") val cursor = database.query(MOVIE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$ID ASC",
                null)

        cursor.moveToFirst()
        var movies: MovieModel
        if (cursor.count > 0){
            do {
                movies = MovieModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE))
                )

                list.add(movies)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }

        cursor.close()
        return list
    }

    @SuppressLint("Recycle")
    fun getMovieById(id: Int):ArrayList<MovieModel>{
        val list = ArrayList<MovieModel>()
        val cursor = database.query(MOVIE_TABLE, null,
                "$ID_DATA = ?",
                arrayOf(id.toString()),
                null,
                null,
                "$ID ASC",
                null)

        cursor.moveToFirst()
        var movie: MovieModel
        if (cursor.count > 0){
            do {
                movie = MovieModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID_DATA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE))
                )

                list.add(movie)
            }while (!cursor.isAfterLast)
        }

        cursor.close()
        return list
    }

    fun insertToFavorite(movie: MovieModel): Long{
        val args = ContentValues()
        args.put(ID_DATA, movie.idData)
        args.put(TITLE, movie.title)
        args.put(POSTER_PATH, movie.posterPath)
        args.put(BACKDROP_PATH, movie.backdropPath)
        args.put(OVERVIEW, movie.overview)
        args.put(VOTE_AVERAGE, movie.voteAverage)
        args.put(RELEASE_DATE, movie.releaseDate)

        return database.insert(MOVIE_TABLE, null, args)
    }

    fun deleteFromFavorite(id: Int) : Int =
            database.delete(MOVIE_TABLE, "$ID = '$id'", null)
}