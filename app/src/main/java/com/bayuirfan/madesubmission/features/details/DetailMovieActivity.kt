package com.bayuirfan.madesubmission.features.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bayuirfan.madesubmission.BuildConfig
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.Constant
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        if (supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        showLoading(true)
        getExtraData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun getExtraData() {
        val status = intent.getIntExtra(Constant.TAG_STATUS, 0)
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
    }

    companion object {
        const val EXTRA_DETAIL = "extra_movie_detail"
    }
}
