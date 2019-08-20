package com.bayuirfan.madesubmission.features

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.features.dashboard.movie.MovieFragment
import com.bayuirfan.madesubmission.features.dashboard.tvshow.TvShowFragment
import com.bayuirfan.madesubmission.features.favorites.FavoriteFragment
import com.bayuirfan.madesubmission.utils.Constant.LOAD_FROM_INTERNET
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.menu_movie -> {
                    setFragment(MovieFragment.getInstance(LOAD_FROM_INTERNET), savedInstanceState)
                }
                R.id.menu_tv_show -> {
                    setFragment(TvShowFragment.getInstance(LOAD_FROM_INTERNET), savedInstanceState)
                }
                R.id.menu_favorite -> {
                    setFragment(FavoriteFragment(), savedInstanceState)
                }
            }
            true
        }
        bottom_navigation_view.selectedItemId = R.id.menu_movie
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFragment(fragment: Fragment?, savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            fragment?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container_main, it)
                        .commit()
            }
        }
    }
}
