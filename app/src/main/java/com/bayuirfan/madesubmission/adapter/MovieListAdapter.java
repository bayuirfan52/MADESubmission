package com.bayuirfan.madesubmission.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.model.MovieModel;

import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MovieModel> movies;

    public MovieListAdapter(Context context){
        this.context = context;
        movies = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        MovieModel model = (MovieModel) getItem(position);
        viewHolder.bind(model);

        return convertView;
    }

    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }

    class ViewHolder{
        private TextView tvTitle, tvAired, tvGenre;
        private ImageView imgPoster;

        ViewHolder(View view){
            tvTitle = view.findViewById(R.id.tv_title_list);
            tvAired = view.findViewById(R.id.tv_aired_list);
            tvGenre = view.findViewById(R.id.tv_genre_list);
            imgPoster = view.findViewById(R.id.img_list);
        }

        @SuppressLint("SetTextI18n")
        void bind(MovieModel movieModel){
            tvTitle.setText(movieModel.getTitle());
            tvAired.setText("Aired : " + movieModel.getYearAired());
            tvGenre.setText("Genre : " + movieModel.getGenre());
            imgPoster.setImageResource(movieModel.getPoster());
        }

    }
}
