package com.bayuirfan.madesubmission.features;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bayuirfan.madesubmission.R;
import com.bayuirfan.madesubmission.adapter.MainPagerAdapter;
import com.bayuirfan.madesubmission.features.dashboard.MovieFragment;
import com.bayuirfan.madesubmission.features.dashboard.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupComponent(){
        TabLayout tabMain = findViewById(R.id.tl_main);
        ViewPager viewPager = findViewById(R.id.vp_main);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this.getSupportFragmentManager());
        mainPagerAdapter.add(new MovieFragment(), getResources().getString(R.string.movie));
        mainPagerAdapter.add(new TvShowFragment(), getResources().getString(R.string.tv_show));
        viewPager.setAdapter(mainPagerAdapter);
        tabMain.setupWithViewPager(viewPager);
    }
}
