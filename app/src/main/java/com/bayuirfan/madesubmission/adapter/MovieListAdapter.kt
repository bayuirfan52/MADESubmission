package com.bayuirfan.madesubmission.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.MovieModel
import java.util.*

class MovieListAdapter : BaseAdapter() {
    private var movies: ArrayList<MovieModel>
    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val viewHolder = ViewHolder(convertView)
        val model = getItem(position) as MovieModel
        viewHolder.bind(model)
        return convertView
    }

    fun setMovies(movies: ArrayList<MovieModel>) {
        this.movies = movies
    }

    internal inner class ViewHolder(view: View) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title_list)
        private val tvAired: TextView = view.findViewById(R.id.tv_aired_list)
        private val tvGenre: TextView = view.findViewById(R.id.tv_genre_list)
        private val imgPoster: ImageView = view.findViewById(R.id.img_list)

        @SuppressLint("SetTextI18n")
        fun bind(movieModel: MovieModel) {
            tvTitle.text = movieModel.title
            tvAired.text = "Aired : " + movieModel.yearAired
            tvGenre.text = "Genre : " + movieModel.genre
            imgPoster.setImageResource(movieModel.poster)
        }

    }

    init {
        movies = ArrayList()
    }
}