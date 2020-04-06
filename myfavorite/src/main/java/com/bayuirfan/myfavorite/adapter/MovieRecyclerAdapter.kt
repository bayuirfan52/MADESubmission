package com.bayuirfan.myfavorite.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bayuirfan.myfavorite.*
import com.bayuirfan.myfavorite.model.MovieModel
import com.bayuirfan.myfavorite.utils.Constant.IMG_W185
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class MovieRecyclerAdapter(
        private val context: Context,
        private val movieModel: ArrayList<MovieModel>): RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, p0, false))

    override fun getItemCount(): Int = movieModel.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(movieModel[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(model: MovieModel){
            Glide.with(context)
                    .load("${BuildConfig.POSTER_BASE_URL}$IMG_W185${model.poster_path}")
                    .into(itemView.img_list)

            itemView.tv_title_list.text = model.title
            itemView.tv_aired_list.text = model.release_date
        }
    }
}