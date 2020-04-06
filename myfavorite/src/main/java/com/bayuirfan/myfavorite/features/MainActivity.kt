package com.bayuirfan.myfavorite.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bayuirfan.myfavorite.R
import com.bayuirfan.myfavorite.adapter.MainPagerAdapter
import com.bayuirfan.myfavorite.features.movie.MovieFragment
import com.bayuirfan.myfavorite.features.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        pagerAdapter.add(MovieFragment(), getString(R.string.movie))
        pagerAdapter.add(TvShowFragment(), getString(R.string.tv_show))

        vp_main.adapter = pagerAdapter
        tab_main.setupWithViewPager(vp_main)
    }
}
