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
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter;
import com.bayuirfan.madesubmission.features.details.DetailMovieActivity;
import com.bayuirfan.madesubmission.model.Constant;
import com.bayuirfan.madesubmission.model.TvShowModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {


    public TvShowFragment() {
        // Required empty public constructor
    }

    private TvShowRecyclerAdapter adapter;
    private String[] title, description, aired, genre, score, season;
    private TypedArray image;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TvShowRecyclerAdapter(getContext());
        recyclerView.setAdapter(adapter);
        loadAllArrayData();
        insertAllIntoArrayList();
        recyclerView.setHasFixedSize(true);
        adapter.setOnItemClickCallback(this::goToDetails);
    }

    private void goToDetails(TvShowModel tvShowModel) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_DETAIL, tvShowModel);
        intent.putExtra(Constant.TAG_STATUS, 2);
        startActivity(intent);
    }

    private void loadAllArrayData(){
        title = getResources().getStringArray(R.array.tv_title);
        description = getResources().getStringArray(R.array.tv_description);
        aired = getResources().getStringArray(R.array.tv_aired);
        genre = getResources().getStringArray(R.array.tv_genre);
        score = getResources().getStringArray(R.array.tv_scored);
        season = getResources().getStringArray(R.array.tv_seasons);
        image = getResources().obtainTypedArray(R.array.tv_poster);
    }

    private void insertAllIntoArrayList(){
        ArrayList<TvShowModel> models = new ArrayList<>();
        for (int i = 0;i < title.length; i++){
            TvShowModel model = new TvShowModel();
            model.setTitle(title[i]);
            model.setDescription(description[i]);
            model.setSeasons(season[i]);
            model.setGenre(genre[i]);
            model.setFirstAired(aired[i]);
            model.setScore(score[i]);
            model.setPoster(image.getResourceId(i, -1));
            models.add(model);
        }
        adapter.setModels(models);
    }
}
