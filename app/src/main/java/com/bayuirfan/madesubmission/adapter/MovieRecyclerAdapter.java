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
import com.bayuirfan.madesubmission.model.MovieModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieModel> movieModels;
    private OnItemClickCallback onItemClickCallback;

    public MovieRecyclerAdapter(Context context) {
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
                .load(getMovieModels().get(i).getPoster())
                .into(viewHolder.imgMovie);

        viewHolder.tvTitle.setText(getMovieModels().get(i).getTitle());
        viewHolder.tvAired.setText(getMovieModels().get(i).getYearAired());
        viewHolder.tvGenre.setText(getMovieModels().get(i).getGenre());
        viewHolder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(movieModels.get(viewHolder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return getMovieModels().size();
    }

    public void setMovieModels(ArrayList<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }

    private ArrayList<MovieModel> getMovieModels() {
        return movieModels;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
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
        void onItemClicked(MovieModel movieModel);
    }
}
