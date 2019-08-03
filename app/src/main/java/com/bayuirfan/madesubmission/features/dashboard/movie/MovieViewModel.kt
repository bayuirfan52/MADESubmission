package com.bayuirfan.madesubmission.features.dashboard.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MovieViewModel: ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<MovieModel>>()
    private val compositeSubscription = CompositeSubscription()

    fun getMovieList(): MutableLiveData<Discover<MovieModel>>{
        val subscription = service.getMovieList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe ({ data ->
                    response.value = data
                },{err ->
                    Log.e("ERROR",err.message)
                })

        compositeSubscription.add(subscription)
        return response
    }
}