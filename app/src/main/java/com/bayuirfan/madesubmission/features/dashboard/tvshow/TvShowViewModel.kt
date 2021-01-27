package com.bayuirfan.madesubmission.features.dashboard.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.model.local.database
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class TvShowViewModel : ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<TvShowModel>>()
    private val data = MutableLiveData<ArrayList<TvShowModel>>()
    private val compositeSubscription = CompositeSubscription()

    fun getTvShowData(): MutableLiveData<Discover<TvShowModel>> {
        val subscription = service.getTvShowList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ data ->
                    response.value = data
                }, { err ->
                    err.message?.let { Log.e("Error", it) }
                    response.value = null
                })
        compositeSubscription.add(subscription)
        return response
    }

    fun getTvShowLocal(context: Context?): MutableLiveData<ArrayList<TvShowModel>> {
        context?.database?.use {
            val favorite = select(TV_SHOW_TABLE)
                    .parseList(classParser<TvShowModel>())
            data.value = favorite as ArrayList<TvShowModel>
        }

        return data
    }
}