package com.bayuirfan.madesubmission.features.dashboard.tvshow

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class TvShowViewModel: ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<TvShowModel>>()
    private val compositeSubscription = CompositeSubscription()

    fun getTvShowData(): MutableLiveData<Discover<TvShowModel>>{
        val subscription = service.getTvShowList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({data ->
                    response.value = data
                },{err ->
                    Log.e("Error",err.message)
                })
        compositeSubscription.add(subscription)
        return response
    }
}