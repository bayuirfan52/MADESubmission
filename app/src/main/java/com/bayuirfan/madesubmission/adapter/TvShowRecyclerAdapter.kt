package com.bayuirfan.madesubmission.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.model.data.TvShowModel
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowRecyclerAdapter(
        private val context: Context,
        private val tvShowModel: List<TvShowModel>,
        private val callback: OnItemClickCallback) : RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(tvShowModel[i])
    }

    override fun getItemCount(): Int = tvShowModel.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowModel: TvShowModel){
            itemView.tv_title_list.text = tvShowModel.original_name
            itemView.tv_aired_list.text = tvShowModel.first_air_date

            itemView.setOnClickListener {
                callback.onItemClicked(tvShowModel)
            }
        }
    }

    companion object {
        interface OnItemClickCallback {
            fun onItemClicked(tvShowModel: TvShowModel)
        }
    }
}
