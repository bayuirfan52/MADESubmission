package com.bayuirfan.madesubmission.model.remote

import com.bayuirfan.madesubmission.BuildConfig
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.data.TvShowModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface MovieCatalogueInterface {
    // ----- Discover -----
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&include_adult=false")
    fun getMovieList(): Observable<Discover<MovieModel>>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getTvShowList() : Observable<Discover<TvShowModel>>

    // ----- Search -----
    @GET("search/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun searchMovieWithName(@Query("query") movieName: String) : Observable<Discover<MovieModel>>

    @GET("search/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun searchTvShowWithName(@Query("query") tvShowName: String) : Observable<Discover<MovieModel>>

    // ----- Release -----
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&include_adult=false")
    fun getMovieReleaseToday(@Query("primary_release_date.gte") TODAY_DATE1: String,
                             @Query("primary_release_date.lte") TODAY_DATE2: String) : Observable<Discover<MovieModel>>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US&include_adult=false")
    fun getTvShowReleaseToday(@Query("primary_release_date.gte") TODAY_DATE1: String,
                             @Query("primary_release_date.lte") TODAY_DATE2: String) : Observable<Discover<TvShowModel>>
}