package com.bayuirfan.madesubmission.features.dashboard.movie

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

class MovieViewModel: ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<MovieModel>>()
    private val result = MutableLiveData<ArrayList<MovieModel>>()
    private val compositeDisposable = CompositeDisposable()
    private val compositeSubscription = CompositeSubscription()

    fun getMovieList(): MutableLiveData<Discover<MovieModel>>{
        val subscription = service.getMovieList()
                .observeOn(AndroidScheduler.mainThread())
                .subscribeOn(Scheduler.newThread())
                .subscribe ({ data ->
                    response.value = data
                },{err ->
                    err.message?.let { Log.e("ERROR",it)}
                    response.value = null
                })

        compositeSubscription.add(subscription)
        return response
    }

    fun getMovieLocalList(context: Context?): MutableLiveData<ArrayList<MovieModel>> {
        val database = context?.let { CatalogueDatabase.getInstance(it) }
        database?.let {
            compositeDisposable.add(
                    it.movieDao().loadAllFavorites()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe({data ->
                                this.result.value = data as ArrayList<MovieModel>
                            },{error ->
                                this.result.value = null
                                error.message?.let { msg -> Log.e("Database error", msg)}
                            })
            )
        }

        return result
    }

    fun searchMovieByName(name: String): MutableLiveData<Discover<MovieModel>>{
        val subscription = service.searchMovieWithName(name)
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