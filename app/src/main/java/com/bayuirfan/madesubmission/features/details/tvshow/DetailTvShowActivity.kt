package com.bayuirfan.madesubmission.features.details.tvshow

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bayuirfan.madesubmission.*
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.model.local.CatalogueDatabase
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.Constant.IMG_W185
import com.bayuirfan.madesubmission.utils.FormatDate
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_tv_show.*

class DetailTvShowActivity : AppCompatActivity(){
    private var menu: Menu? = null
    private var isFavorite: Boolean = false
    private var data: TvShowModel? = null
    private var database: CatalogueDatabase? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle(R.string.detail_tv_show)
        }
        database = CatalogueDatabase.getInstance(this)
        data = intent.getParcelableExtra(EXTRA_DETAIL)
        loadFromTvShow()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        setFavoriteState()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.check_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                }
                else {
                    addToFavorite()
                }
                isFavorite = !isFavorite
                setFavoriteState()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkIsFavorite(model: TvShowModel){
        database?.let { it ->
            compositeDisposable.add(
                    it.tvShowDao().loadFavoritesById(model.idData)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.computation())
                            .subscribe ({ data ->
                                isFavorite = data != null
                            },{ error ->
                                isFavorite = false
                                error.message?.let{ msg -> Log.e("Check Favorite", msg)}
                            })
            )
        }
    }

    private fun setFavoriteState(){
        if (isFavorite) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_uncheck)
        }
    }

    private fun showLoading(value: Boolean){
        if(value){
            img_detail.visibility = View.GONE
            tv_title_detail.visibility = View.GONE
            tv_description_detail.visibility = View.GONE
            tv_score_detail.visibility = View.GONE
            tv_aired_detail.visibility = View.GONE
            progress_detail.visibility = View.VISIBLE
        } else {
            img_detail.visibility = View.VISIBLE
            tv_title_detail.visibility = View.VISIBLE
            tv_description_detail.visibility = View.VISIBLE
            tv_score_detail.visibility = View.VISIBLE
            tv_aired_detail.visibility = View.VISIBLE
            progress_detail.visibility = View.GONE
        }
    }

    private fun loadFromTvShow(){
        showLoading(true)
        data?.let {
            Glide.with(this)
                    .load("${BuildConfig.POSTER_BASE_URL}$IMG_W185${it.posterPath}")
                    .into(img_detail)
            tv_title_detail.text = it.name
            tv_aired_detail.text = it.firstAirDate?.let { it1 -> FormatDate.reformatDate(it1) }
            tv_score_detail.text = it.voteAverage
            tv_description_detail.text = it.overview
            checkIsFavorite(it)
        }
        showLoading(false)
    }

    private fun addToFavorite() {
        compositeDisposable.add(Observable.fromCallable {
                data?.let {
                    database?.tvShowDao()?.insertIntoFavorites(it)
                }
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe()
        )

        showSnackbarMessage(getString(R.string.added_message))
    }

    private fun removeFromFavorite() {
        compositeDisposable.add(Observable.fromCallable {
            data?.let {
                database?.tvShowDao()?.removeFromFavorites(it)
            }
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe()
        )

        showSnackbarMessage(getString(R.string.removed_message))
    }

    private fun showSnackbarMessage(message: String){
        Snackbar.make(detail_layout, message, Snackbar.LENGTH_LONG).show()
    }
}