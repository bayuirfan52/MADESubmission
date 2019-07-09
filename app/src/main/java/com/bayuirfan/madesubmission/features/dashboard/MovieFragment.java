package com.bayuirfan.madesubmission.features.dashboard;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter;
import com.bayuirfan.madesubmission.features.details.DetailMovieActivity;
import com.bayuirfan.madesubmission.model.MovieModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    public MovieFragment() {
        // Required empty public constructor
    }

    private MovieRecyclerAdapter adapter;
    private String[] title, description, aired, genre, score, duration;
    private TypedArray image;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        loadAllArrayData();
        insertAllIntoArrayList();
        adapter.setOnItemClickCallback(this::goToDetails);
    }

    private void loadAllArrayData(){
        title = getResources().getStringArray(R.array.movie_title);
        description = getResources().getStringArray(R.array.movie_description);
        aired = getResources().getStringArray(R.array.movie_aired);
        genre = getResources().getStringArray(R.array.movie_genre);
        score = getResources().getStringArray(R.array.movie_scored);
        duration = getResources().getStringArray(R.array.movie_duration);
        image = getResources().obtainTypedArray(R.array.movie_poster);
    }

    private void insertAllIntoArrayList(){
        ArrayList<MovieModel> movies = new ArrayList<>();
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
        adapter.setMovieModels(movies);
    }

    private void goToDetails(MovieModel movieModel){
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_DETAIL, movieModel);
        startActivity(intent);
    }
}
