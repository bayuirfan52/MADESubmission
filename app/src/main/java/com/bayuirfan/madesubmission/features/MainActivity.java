package com.bayuirfan.madesubmission.features;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.adapter.MovieListAdapter;
import com.bayuirfan.madesubmission.model.MovieModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] title, description, aired, genre, score, duration;
    private TypedArray image;
    private MovieListAdapter adapter;
    private ArrayList<MovieModel> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieListAdapter(this);
        ListView listView = findViewById(R.id.lv_movies);
        listView.setAdapter(adapter);

        loadData();
        setupComponent();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, DetailMovieActivity.class);

            MovieModel model = new MovieModel();
            model.setTitle(movies.get(position).getTitle());
            model.setPoster(movies.get(position).getPoster());
            model.setScore(movies.get(position).getScore());
            model.setYearAired(movies.get(position).getYearAired());
            model.setGenre(movies.get(position).getGenre());
            model.setDuration(movies.get(position).getDuration());
            model.setDescription(movies.get(position).getDescription());

            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_DETAIL, model);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void loadData(){
        title = getResources().getStringArray(R.array.movie_title);
        description = getResources().getStringArray(R.array.movie_description);
        aired = getResources().getStringArray(R.array.movie_aired);
        genre = getResources().getStringArray(R.array.movie_genre);
        score = getResources().getStringArray(R.array.movie_scored);
        duration = getResources().getStringArray(R.array.movie_duration);
        image = getResources().obtainTypedArray(R.array.movie_poster);
    }

    private void setupComponent(){
        movies = new ArrayList<>();
            for (int i = 0;i < title.length; i++){
                MovieModel model = new MovieModel();
                model.setTitle(title[i]);
                model.setDescription(description[i]);
                model.setDuration(duration[i]);
                model.setGenre(genre[i]);
                model.setYearAired(aired[i]);
                model.setScore(score[i]);
                model.setPoster(image.getResourceId(i, -1));
                movies.add(model);
            }
            adapter.setMovies(movies);
    }
}
