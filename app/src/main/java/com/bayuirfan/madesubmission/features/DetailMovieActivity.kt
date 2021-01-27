package com.bayuirfan.madesubmission.features

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.MovieModel

class DetailMovieActivity : AppCompatActivity() {
    private var image: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvDescription: TextView? = null
    private var tvGenre: TextView? = null
    private var tvAired: TextView? = null
    private var tvScore: TextView? = null
    private var tvDuration: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Movie"
        setupComponent()
        extraData
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun setupComponent() {
        image = findViewById(R.id.img_detail)
        tvTitle = findViewById(R.id.tv_title_detail)
        tvDescription = findViewById(R.id.tv_description_detail)
        tvAired = findViewById(R.id.tv_aired_detail)
        tvGenre = findViewById(R.id.tv_genre_detail)
        tvScore = findViewById(R.id.tv_score_detail)
        tvDuration = findViewById(R.id.tv_duration_detail)
    }

    @get:SuppressLint("SetTextI18n")
    private val extraData: Unit
        get() {
            val movie: MovieModel = intent.getParcelableExtra(EXTRA_MOVIE_DETAIL)!!
            image!!.setImageResource(movie.poster)
            tvTitle!!.text = movie.title
            tvDescription!!.text = movie.description
            tvGenre!!.text = "Genre : " + movie.genre
            tvAired!!.text = "Aired : " + movie.yearAired
            tvScore!!.text = "Score : " + movie.score
            tvDuration!!.text = "Duration : " + movie.duration
        }

    companion object {
        const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"
    }
}