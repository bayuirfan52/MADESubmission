package com.bayuirfan.madesubmission.features.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.Constant

class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

                    supportActionBar!!.setTitle(R.string.detail_movie)
                }
                2 -> {

                    supportActionBar!!.setTitle(R.string.detail_tv_show)
                }
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_movie_detail"
    }
}
