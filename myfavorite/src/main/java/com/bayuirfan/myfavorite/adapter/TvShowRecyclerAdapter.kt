package com.bayuirfan.myfavorite.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bayuirfan.myfavorite.*
import com.bayuirfan.myfavorite.model.TvShowModel
import com.bayuirfan.myfavorite.utils.Constant.IMG_W185
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowRecyclerAdapter(
        private val context: Context,
        private val tvShowModel: ArrayList<TvShowModel>): RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, p0, false))

    override fun getItemCount(): Int = tvShowModel.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(tvShowModel[p1])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowModel: TvShowModel){
            Glide.with(context)
                    .load("${BuildConfig.POSTER_BASE_URL}$IMG_W185${tvShowModel.poster_path}")
                    .into(itemView.img_list)

            itemView.tv_title_list.text = tvShowModel.name
            itemView.tv_aired_list.text = tvShowModel.first_air_date
        }
    }
}