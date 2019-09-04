package com.bayuirfan.madesubmission.services

import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.*
import com.bayuirfan.madesubmission.*
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.local.CatalogueDatabase
import com.bayuirfan.madesubmission.utils.Constant
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_ITEM
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_DATE
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieStackWidgetRemoteFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory{
    private val listData= mutableListOf<MovieModel>()
    private val compositeDisposable = CompositeDisposable()
    private var database : CatalogueDatabase? = null

    override fun onCreate() {
        database = CatalogueDatabase.getInstance(context)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        database?.let {
            compositeDisposable.add(
                    it.movieDao().loadAllFavorites()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe { data ->
                                listData.addAll(data)
                            }
            )
        }
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.catalogue_widget_item)

        val id = listData[position].ids
        val idData = listData[position].idData
        val title = listData[position].title
        val posterPath = listData[position].posterPath
        val backdropPath = listData[position].backdropPath
        val overview = listData[position].overview
        val voteAverage = listData[position].voteAverage
        val releaseDate = listData[position].releaseDate

        @Suppress("DEPRECATION") val bitmap : Bitmap = Glide.with(context)
                .asBitmap()
                .load("${BuildConfig.POSTER_BASE_URL}${Constant.IMG_W185}${posterPath}")
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
        rv.setImageViewBitmap(R.id.catalogue_image_widget, bitmap)
        rv.setTextViewText(R.id.banner_text, title)

        val extras = Bundle()
        extras.putInt(ID, id)
        extras.putInt(ID_DATA, idData)
        extras.putString(TITLE, title)
        extras.putString(POSTER_PATH, posterPath)
        extras.putString(BACKDROP_PATH, backdropPath)
        extras.putString(OVERVIEW, overview)
        extras.putString(VOTE_AVERAGE, voteAverage)
        extras.putString(RELEASE_DATE, releaseDate)

        val fillInIntent = Intent()
        fillInIntent.putExtra(EXTRA_ITEM, extras)
        rv.setOnClickFillInIntent(R.id.catalogue_image_widget, fillInIntent)
        return rv
    }

    override fun getCount(): Int = listData.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}