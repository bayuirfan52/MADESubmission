package com.bayuirfan.madesubmission.features.details.tvshow

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bayuirfan.madesubmission.BuildConfig
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.model.local.database
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.IMG_W185
import com.bayuirfan.madesubmission.utils.Constant.NAME
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.TV_SHOW_TABLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE
import com.bayuirfan.madesubmission.utils.FormatDate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTvShowActivity : AppCompatActivity(){
    private var menu: Menu? = null
    private var isFavorite: Boolean = false
    private var data: TvShowModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle(R.string.detail_tv_show)
        }
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
        database.use {
            val result = select(TV_SHOW_TABLE)
                    .whereArgs(
                            "($ID = {id})",
                            "id" to model.ids
                    )
            val favorite = result.parseList(classParser<MovieModel>())
            if(favorite.isNotEmpty()) isFavorite = true
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

    private fun addToFavorite() = try {
        database.use {
            insert(TV_SHOW_TABLE,
                    ID_DATA to data?.idData,
                    NAME to data?.name,
                    POSTER_PATH to data?.posterPath,
                    BACKDROP_PATH to data?.backdropPath,
                    OVERVIEW to data?.overview,
                    VOTE_AVERAGE to data?.voteAverage,
                    FIRST_AIR_DATE to data?.firstAirDate)
        }
        showSnackbarMessage(getString(R.string.added_message))
    } catch (e: SQLiteConstraintException){
        showSnackbarMessage(e.localizedMessage!!)
    }

    private fun removeFromFavorite() = try {
        database.use {
            data?.let {
                delete(TV_SHOW_TABLE, "($ID  = {id})", "id" to it.ids)
            }
        }
        showSnackbarMessage(getString(R.string.removed_message))
    } catch (e : SQLiteConstraintException){
        showSnackbarMessage(e.localizedMessage!!)
    }

    private fun showSnackbarMessage(message: String){
        Snackbar.make(detail_layout, message, Snackbar.LENGTH_LONG).show()
    }
}