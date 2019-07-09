package com.bayuirfan.madesubmission.features;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.model.MovieModel;

public class DetailMovieActivity extends AppCompatActivity {
    private ImageView image;
    private TextView tvTitle, tvDescription, tvGenre, tvAired, tvScore, tvDuration;
    public static final String EXTRA_MOVIE_DETAIL = "extra_movie_detail";

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Movie");
        setupComponent();
        getExtraData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
    }

    @SuppressLint("SetTextI18n")
    private void getExtraData(){
        MovieModel movie = getIntent().getParcelableExtra(EXTRA_MOVIE_DETAIL);
        image.setImageResource(movie.getPoster());
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getDescription());
        tvGenre.setText("Genre : " + movie.getGenre());
        tvAired.setText("Aired : " + movie.getYearAired());
        tvScore.setText("Score : " + movie.getScore());
        tvDuration.setText("Duration : " + movie.getDuration());
    }
}
