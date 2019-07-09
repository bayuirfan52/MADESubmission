package com.bayuirfan.madesubmission.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.model.TvShowModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TvShowRecyclerAdapter extends RecyclerView.Adapter<TvShowRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TvShowModel> models;
    private OnItemClickCallback onItemClickCallback;

    public TvShowRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context)
                .load(getModels().get(i).getPoster())
                .into(viewHolder.imgMovie);

        viewHolder.tvTitle.setText(getModels().get(i).getTitle());
        viewHolder.tvAired.setText(getModels().get(i).getFirstAired());
        viewHolder.tvGenre.setText(getModels().get(i).getGenre());
        viewHolder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(models.get(viewHolder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private ArrayList<TvShowModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<TvShowModel> models) {
        this.models = models;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgMovie;
        TextView tvTitle, tvAired, tvGenre;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_list);
            tvTitle = itemView.findViewById(R.id.tv_title_list);
            tvAired = itemView.findViewById(R.id.tv_aired_list);
            tvGenre = itemView.findViewById(R.id.tv_genre_list);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(TvShowModel tvShowModel);
    }
}
