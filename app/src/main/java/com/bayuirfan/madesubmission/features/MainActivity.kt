package com.bayuirfan.madesubmission.features

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieListAdapter
import com.bayuirfan.madesubmission.model.MovieModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var title: Array<String>
    private lateinit var description: Array<String>
    private lateinit var aired: Array<String>
    private lateinit var genre: Array<String>
    private lateinit var score: Array<String>
    private lateinit var duration: Array<String>
    private var image: TypedArray? = null
    private var adapter: MovieListAdapter? = null
    private var movies: ArrayList<MovieModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MovieListAdapter()
        val listView: ListView = findViewById(R.id.lv_movies)
        listView.adapter = adapter
        loadData()
        setupComponent()
        listView.onItemClickListener = AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            val intent = Intent(this, DetailMovieActivity::class.java)
            val model = MovieModel()
            model.title = movies!![position].title
            model.poster = movies!![position].poster
            model.score = movies!![position].score
            model.yearAired = movies!![position].yearAired
            model.genre = movies!![position].genre
            model.duration = movies!![position].duration
            model.description = movies!![position].description
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_DETAIL, model)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun loadData() {
        title = resources.getStringArray(R.array.movie_title)
        description = resources.getStringArray(R.array.movie_description)
        aired = resources.getStringArray(R.array.movie_aired)
        genre = resources.getStringArray(R.array.movie_genre)
        score = resources.getStringArray(R.array.movie_scored)
        duration = resources.getStringArray(R.array.movie_duration)
        image = resources.obtainTypedArray(R.array.movie_poster)
    }

    private fun setupComponent() {
        movies = ArrayList<MovieModel>()
        for (i in title.indices) {
            val model = MovieModel()
            model.title = title[i]
            model.description = description[i]
            model.duration = duration[i]
            model.genre = genre[i]
            model.yearAired = aired[i]
            model.score = score[i]
            model.poster = image?.getResourceId(i, -1)!!
            movies!!.add(model)
        }
        adapter?.setMovies(movies!!)
    }
}