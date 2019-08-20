package com.bayuirfan.madesubmission.features.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bayuirfan.madesubmission.BuildConfig
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.model.local.MovieOpenHelper
import com.bayuirfan.madesubmission.model.local.TvShowOpenHelper
import com.bayuirfan.madesubmission.utils.Constant
import com.bayuirfan.madesubmission.utils.Constant.TAG_STATUS
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {
    private var menu: Menu? = null
    private var isFavorite : Boolean = false
    private lateinit var movieOpenHelper: MovieOpenHelper
    private lateinit var tvShowOpenHelper: TvShowOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        if (supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieOpenHelper = MovieOpenHelper.getInstance(this.applicationContext)
        tvShowOpenHelper = TvShowOpenHelper.getInstance(this.applicationContext)

        showLoading(true)
        getExtraData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()

            R.id.check_favorite -> {
                if(isFavorite)
                    removeFromFavorite()
                else
                    addToFavorite()
            }
        }
        return true
    }

    private fun removeFromFavorite(){

    }

    private fun addToFavorite(){

    }

    private fun getExtraData() {
        val status = intent.getIntExtra(TAG_STATUS, 0)
        if (supportActionBar != null) {
            when (status) {
                1 -> {
                    loadFromMovie()
                    supportActionBar?.setTitle(R.string.detail_movie)
                }
                2 -> {
                    loadFromTvShow()
                    supportActionBar?.setTitle(R.string.detail_tv_show)
                }
            }
            showLoading(false)
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

    private fun loadFromMovie(){
        val data: MovieModel = intent.getParcelableExtra(EXTRA_DETAIL)
        Glide.with(this)
                .load("${BuildConfig.POSTER_BASE_URL}${Constant.IMG_W185}${data.posterPath}")
                .into(img_detail)

        tv_title_detail.text = data.title
        tv_aired_detail.text = data.releaseDate
        tv_score_detail.text = data.voteAverage
        tv_description_detail.text = data.overview

        isFavorite = inMovieFavorite(data.idData)
    }

    private fun loadFromTvShow(){
        val data: TvShowModel = intent.getParcelableExtra(EXTRA_DETAIL)
        Glide.with(this)
                .load("${BuildConfig.POSTER_BASE_URL}${Constant.IMG_W185}${data.posterPath}")
                .into(img_detail)
        tv_title_detail.text = data.name
        tv_aired_detail.text = data.firstAirDate
        tv_score_detail.text = data.voteAverage
        tv_description_detail.text = data.overview

        isFavorite = inTvShowFavorite(data.idData)
    }

    private fun inMovieFavorite(id: Int): Boolean{
        val list = movieOpenHelper.getMovieById(id)
        return list.isNotEmpty()
    }

    private fun inTvShowFavorite(id: Int): Boolean{
        val list = tvShowOpenHelper.getTvShowById(id)
        return list.isNotEmpty()
    }

    companion object {
        const val EXTRA_DETAIL = "extra_movie_detail"
    }
}
