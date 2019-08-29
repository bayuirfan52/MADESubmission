package com.bayuirfan.madesubmission.features.dashboard.movie

import android.arch.lifecycle.*
import android.content.Context
import android.util.Log
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.model.local.database
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import org.jetbrains.anko.db.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MovieViewModel: ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<MovieModel>>()
    private val result = MutableLiveData<ArrayList<MovieModel>>()
    private val compositeSubscription = CompositeSubscription()

    fun getMovieList(): MutableLiveData<Discover<MovieModel>>{
        val subscription = service.getMovieList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe ({ data ->
                    response.value = data
                },{err ->
                    Log.e("ERROR",err.message)
                    response.value = null
                })

        compositeSubscription.add(subscription)
        return response
    }

    fun getMovieLocalList(context: Context?): MutableLiveData<ArrayList<MovieModel>> {
        context?.database?.use {
            val local = select(MOVIE_TABLE)
                    .parseList(classParser<MovieModel>())
            result.value = local as ArrayList<MovieModel>
        }
        return result
    }

    fun searchMovieByName(name: String): MutableLiveData<Discover<MovieModel>>{
        val subscription = service.searchMovieWithName(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({data ->
                    response.value = data
                },{error ->
                    Log.e("ERROR", error.message)
                    response.value = null
                })

        compositeSubscription.add(subscription)
        return response
    }
}