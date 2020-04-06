package com.bayuirfan.madesubmission.adapter

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bayuirfan.madesubmission.*
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.utils.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowRecyclerAdapter(
        private val context: Context,
        private val tvShowModel: ArrayList<TvShowModel>,
        private val callback: OnItemClickCallback<TvShowModel>) : RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(tvShowModel[i])
    }

    override fun getItemCount(): Int = tvShowModel.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowModel: TvShowModel){
            itemView.tv_title_list.text = tvShowModel.name
            itemView.tv_aired_list.text = tvShowModel.firstAirDate?.let { FormatDate.reformatDate(it) }
            Glide.with(context)
                    .load("${BuildConfig.POSTER_BASE_URL}${Constant.IMG_W185}${tvShowModel.posterPath}")
                    .into(itemView.img_list)

            itemView.setOnClickListener {
                callback.onItemClicked(tvShowModel)
            }
        }
    }
}
