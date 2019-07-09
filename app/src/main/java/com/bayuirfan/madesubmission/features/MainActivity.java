package com.bayuirfan.madesubmission.features;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.adapter.MainPagerAdapter;
import com.bayuirfan.madesubmission.features.dashboard.MovieFragment;
import com.bayuirfan.madesubmission.features.dashboard.TvShowFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponent();
    }

    private void setupComponent(){
        TabLayout tabMain = findViewById(R.id.tl_main);
        ViewPager viewPager = findViewById(R.id.vp_main);
        fragments.add(new MovieFragment());
        fragments.add(new TvShowFragment());
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainPagerAdapter);
        tabMain.setupWithViewPager(viewPager);
    }
}
