package com.bayuirfan.madesubmission.features.dashboard.tvshow

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.model.local.CatalogueDatabase
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import rx.android.schedulers.AndroidSchedulers as AndroidScheduler
import rx.schedulers.Schedulers as Scheduler

class TvShowViewModel: ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<TvShowModel>>()
    private val data = MutableLiveData<ArrayList<TvShowModel>>()
    private val compositeDisposable = CompositeDisposable()
    private val compositeSubscription = CompositeSubscription()

    fun getTvShowData(): MutableLiveData<Discover<TvShowModel>>{
        val subscription = service.getTvShowList()
                .observeOn(AndroidScheduler.mainThread())
                .subscribeOn(Scheduler.newThread())
                .subscribe({data ->
                    response.value = data
                },{err ->
                    err.message?.let { Log.e("Error",it)}
                    response.value = null
                })
        compositeSubscription.add(subscription)
        return response
    }

    fun getTvShowLocal(context: Context?): MutableLiveData<ArrayList<TvShowModel>>{
        val database = context?.let { CatalogueDatabase.getInstance(it) }
        database?.let {
            compositeDisposable.add(
                    it.tvShowDao().loadAllFavorites()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe({data ->
                                this.data.value = data as ArrayList<TvShowModel>
                            },{error ->
                                error.message?.let {msg ->  Log.e("Database error", msg)}
                                data.value = null
                            })
            )
        }
        return data
    }

    fun searchTvShowByName(name: String): MutableLiveData<Discover<TvShowModel>>{
        val subscription = service.searchTvShowWithName(name)
                .observeOn(AndroidScheduler.mainThread())
                .subscribeOn(Scheduler.newThread())
                .subscribe({data ->
                    response.value = data
                },{error ->
                    error.message?.let { Log.e("ERROR", it)}
                    response.value = null
                })

        compositeSubscription.add(subscription)
        return response
    }
}