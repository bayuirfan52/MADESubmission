package com.bayuirfan.myfavorite.features.movie

import android.content.Context
import androidx.lifecycle.*
import com.bayuirfan.myfavorite.model.MovieModel
import com.bayuirfan.myfavorite.utils.Constant.MOVIE_CONTENT_URI
import com.bayuirfan.myfavorite.utils.CursorMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getAllData(context: Context?): MutableLiveData<ArrayList<MovieModel>> {
        val listMovie = MutableLiveData<ArrayList<MovieModel>>()
        compositeDisposable.add(Observable.fromCallable {
            context?.contentResolver?.query(MOVIE_CONTENT_URI, null, null, null, null)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe {cursor ->
                    listMovie.value = cursor?.let {
                        CursorMapper.mapMovieCursorToArrayList(it)
                    }
                }
        )

        return listMovie
    }
}