package com.bayuirfan.madesubmission.features.dashboard.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.local.database
import com.bayuirfan.madesubmission.model.remote.MovieCatalogueService
import com.bayuirfan.madesubmission.utils.Constant.MOVIE_TABLE
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MovieViewModel : ViewModel() {
    private val service = MovieCatalogueService.getClient()
    private val response = MutableLiveData<Discover<MovieModel>>()
    private val result = MutableLiveData<ArrayList<MovieModel>>()
    private val compositeSubscription = CompositeSubscription()

    fun getMovieList(): MutableLiveData<Discover<MovieModel>> {
        val subscription = service.getMovieList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ data ->
                    response.value = data
                }, { err ->
                    err.message?.let { Log.e("ERROR", it) }
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
}