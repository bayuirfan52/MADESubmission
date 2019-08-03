package com.bayuirfan.madesubmission.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.data.MovieModel
import kotlinx.android.synthetic.main.list_item.view.*

class MovieRecyclerAdapter(
        private val context: Context,
        private val movieModels: List<MovieModel>,
        private val callback: OnItemClickCallback) : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(movieModels[i])
    }

    override fun getItemCount(): Int = movieModels.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieModels: MovieModel) {
            itemView.tv_title_list.text = movieModels.title
            itemView.tv_aired_list.text = movieModels.releaseDate

            itemView.setOnClickListener {
                callback.onItemClicked(movieModels)
            }
        }
    }

    companion object {
        interface OnItemClickCallback {
            fun onItemClicked(movieModels: MovieModel)
        }
    }
}
