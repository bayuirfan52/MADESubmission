package com.bayuirfan.madesubmission.features.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.model.Constant;
import com.bayuirfan.madesubmission.model.MovieModel;
import com.bayuirfan.madesubmission.model.TvShowModel;

public class DetailMovieActivity extends AppCompatActivity {
    private ImageView image;
    private TextView tvTitle, tvDescription, tvGenre, tvAired, tvScore, tvDuration, tvDurationTitle;
    public static final String EXTRA_MOVIE_DETAIL = "extra_movie_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupComponent();
        getExtraData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    private void setupComponent(){
        image = findViewById(R.id.img_detail);
        tvTitle = findViewById(R.id.tv_title_detail);
        tvDescription = findViewById(R.id.tv_description_detail);
        tvAired = findViewById(R.id.tv_aired_detail);
        tvGenre = findViewById(R.id.tv_genre_detail);
        tvScore = findViewById(R.id.tv_score_detail);
        tvDuration = findViewById(R.id.tv_duration_detail);
        tvDurationTitle = findViewById(R.id.duration_title);
    }

    private void getExtraData(){
        int status = getIntent().getIntExtra(Constant.TAG_STATUS, 0);
        if (getSupportActionBar() != null) {
            switch (status) {
                case 1:
                    getExtraDataMovies();
                    getSupportActionBar().setTitle(R.string.detail_movie);
                    break;
                case 2:
                    getExtraDataTvShow();
                    getSupportActionBar().setTitle(R.string.detail_tv_show);
                    break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void getExtraDataMovies(){
        MovieModel movie = getIntent().getParcelableExtra(EXTRA_MOVIE_DETAIL);
        image.setImageResource(movie.getPoster());
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getDescription());
        tvGenre.setText(movie.getGenre());
        tvAired.setText(movie.getYearAired());
        tvScore.setText(movie.getScore());
        tvDuration.setText(movie.getDuration());
        tvDurationTitle.setText(R.string.duration_sample);
    }

    @SuppressLint("SetTextI18n")
    private void getExtraDataTvShow(){
        TvShowModel tvShowModel = getIntent().getParcelableExtra(EXTRA_MOVIE_DETAIL);
        image.setImageResource(tvShowModel.getPoster());
        tvTitle.setText(tvShowModel.getTitle());
        tvDescription.setText(tvShowModel.getDescription());
        tvGenre.setText(tvShowModel.getGenre());
        tvAired.setText(tvShowModel.getFirstAired());
        tvScore.setText(tvShowModel.getScore());
        tvDuration.setText(tvShowModel.getSeasons());
        tvDurationTitle.setText(R.string.season_sample);
    }
}
