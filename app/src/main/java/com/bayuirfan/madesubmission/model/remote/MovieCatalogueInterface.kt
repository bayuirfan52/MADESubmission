package com.bayuirfan.madesubmission.model.remote

import com.bayuirfan.madesubmission.BuildConfig
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.data.TvShowModel
import retrofit2.http.GET
import rx.Observable

interface MovieCatalogueInterface {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US&include_adult=false")
    fun getMovieList(): Observable<Discover<MovieModel>>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getTvShowList() : Observable<Discover<TvShowModel>>
}