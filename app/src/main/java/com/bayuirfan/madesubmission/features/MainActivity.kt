package com.bayuirfan.madesubmission.features

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.features.dashboard.movie.MovieFragment
import com.bayuirfan.madesubmission.features.dashboard.tvshow.TvShowFragment
import com.bayuirfan.madesubmission.features.favorites.FavoriteFragment
import com.bayuirfan.madesubmission.features.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.menu_movie -> {
                    setFragment(MovieFragment())
                }
                R.id.menu_tv_show -> {
                    setFragment(TvShowFragment())
                }
                R.id.menu_favorite -> {
                    setFragment(FavoriteFragment())
                }
            }
            true
        }
        if (savedInstanceState == null)
            bottom_navigation_view.selectedItemId = R.id.menu_movie
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFragment(fragment: Fragment?){
        fragment?.let {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main, it)
                    .commit()
        }
    }
}
