package com.bayuirfan.myfavorite.features.tvshow

import android.content.Context
import androidx.lifecycle.*
import com.bayuirfan.myfavorite.model.TvShowModel
import com.bayuirfan.myfavorite.utils.Constant.TV_SHOW_CONTENT_URI
import com.bayuirfan.myfavorite.utils.CursorMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getAllData(context: Context?): MutableLiveData<ArrayList<TvShowModel>>{
        val listData = MutableLiveData<ArrayList<TvShowModel>>()
        compositeDisposable.add(Observable.fromCallable {
                context?.contentResolver?.query(TV_SHOW_CONTENT_URI, null, null, null, null)
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe {cursor ->
                    listData.value = cursor?.let { CursorMapper.mapTvShowCursorToArrayList(it) }
                }
        )

        return listData
    }
}